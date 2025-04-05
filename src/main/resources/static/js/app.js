document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("usersTable")) {
        fetchUsers();
    } else if (document.getElementById("ordersTable")) {
        fetchOrders();
    } else if (document.getElementById("productsTable")) {
        fetchProducts();
    }

    setupCsvExportButtons(); // CSV出力ボタンをセットアップ
});

/**
 * ユーザー一覧を取得して表示
 */
function fetchUsers() {
    fetch("http://localhost:8080/api/users")
        .then(response => response.json())
        .then(data => {
            let table = document.querySelector("#usersTable tbody");
            table.innerHTML = ""; // 既存の内容をクリア
            data.forEach(user => {
                let row = `<tr>
                    <td>${user.userId}</td>
                    <td>${user.name}</td>
                    <td>${user.address}</td>
                    <td>${user.isMale ? "男" : "女"}</td>
                    <td>${user.old}</td>
                </tr>`;
                table.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching users:", error));
}

/**
 * 注文一覧を取得して表示
 */
function fetchOrders() {
    fetch("http://localhost:8080/api/orders")
        .then(response => response.json())
        .then(data => {
            let table = document.querySelector("#ordersTable tbody");
            table.innerHTML = ""; // 既存の内容をクリア

            // ステータスの日本語変換マップ
            const statusMap = {
                1: "受付",
                2: "決済済み",
                3: "発送準備",
                4: "発送中",
                5: "発送完了"
            };

            data.forEach(order => {
                // 日付フォーマット変換
                let formattedDate = formatDate(order.orderDate);

                let row = `<tr>
                    <td>${order.orderId}</td>
                    <td>${statusMap[order.orderStatus] || "不明"}</td>
                    <td>${order.userId}</td>
                    <td>${order.productId}</td>
                    <td>${order.productName}</td>
                    <td>${order.totalPrice}</td>
                    <td>${formattedDate}</td>
                </tr>`;
                table.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching orders:", error));
}

/**
 * 商品一覧を取得して表示
 */
function fetchProducts() {
    fetch("http://localhost:8080/api/products")
        .then(response => response.json())
        .then(data => {
            let table = document.querySelector("#productsTable tbody");
            table.innerHTML = ""; // 既存の内容をクリア
            data.forEach(product => {
                let row = `<tr>
                    <td>${product.productId}</td>
                    <td>${product.productName}</td>
                    <td>${product.stockQuantity}</td>
                    <td>${product.price}</td>
                    <td>${product.orderAvailability ? "可" : "不可"}</td>
                </tr>`;
                table.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching products:", error));
}


/**
 * 日付を "YYYY/MM/DD HH:mm:ss" にフォーマット
 */
function formatDate(dateArray) {
    try {
        if (!Array.isArray(dateArray) || dateArray.length < 6) return "不明";
        let [year, month, day, hour, minute, second] = dateArray;
        return `${year}/${month}/${day} ${hour}:${minute}:${second}`;
    } catch (error) {
        console.error("Date formatting error:", error);
        return "不明";
    }
}
