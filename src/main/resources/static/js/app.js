document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("usersTable")) {
        fetchUsers();
    } else if (document.getElementById("ordersTable")) {
        fetchOrders();
    } else if (document.getElementById("productsTable")) {
        fetchProducts();
    }
});

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
            console.log(data);
        })
        .catch(error => console.error("Error fetching users:", error));
}

function fetchOrders() {
    fetch("http://localhost:8080/api/orders")
        .then(response => response.json())
        .then(data => {
            let table = document.querySelector("#ordersTable tbody");
            table.innerHTML = ""; // 既存の内容をクリア

            // ステータスの日本語変換マップ
            const statusMap = {
                1: "受付：注文のみの状態",
                2: "決済済み：注文された商品が決済済み",
                3: "発送準備：決済済みの商品が梱包などの準備中",
                4: "発送中：商品の発送を行なっている状態",
                5: "発送完了：発送済み"
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
            console.log(data);
        })
        .catch(error => console.error("Error fetching orders:", error));
}


// 日付フォーマットを "YYYY/MM/DD HH:mm:ss" に変換
function formatDate(dateArray) {
    try {
        if (!Array.isArray(dateArray) || dateArray.length < 6) return "不明"; // 配列でない、またはデータが足りない場合

        let [year, month, day, hour, minute, second] = dateArray; // 分割代入で取得

        return `${year}/${month}/${day} ${hour}:${minute}:${second}`;
    } catch (error) {
        console.error("Date formatting error:", error);
        return "不明";
    }
}


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
            console.log(data);
        })
        .catch(error => console.error("Error fetching products:", error));
}
