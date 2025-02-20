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
    fetch("http://localhost:8080/users")
        .then(response => response.json())
        .then(data => {
            let table = document.querySelector("#usersTable tbody");
            data.forEach(user => {
                let row = `<tr>
                    <td>${user.user_id}</td>
                    <td>${user.name}</td>
                    <td>${user.address}</td>
                    <td>${user.is_male ? "男" : "女"}</td>
                    <td>${user.old}</td>
                </tr>`;
                table.innerHTML += row;
            });
            console.log(table);
        });
}

function fetchOrders() {
    fetch("http://localhost:8080/orders")
        .then(response => response.json())
        .then(data => {
            let table = document.querySelector("#ordersTable tbody");
            data.forEach(order => {
                let row = `<tr>
                    <td>${order.order_id}</td>
                    <td>${order.order_status}</td>
                    <td>${order.user_id}</td>
                    <td>${order.product_id}</td>
                    <td>${order.product_name}</td>
                    <td>${order.total_price}</td>
                    <td>${order.order_date}</td>
                </tr>`;
                table.innerHTML += row;
            });
            console.log(table);
        });
}

function fetchProducts() {
    fetch("http://localhost:8080/products")
        .then(response => response.json())
        .then(data => {
            let table = document.querySelector("#productsTable tbody");
            data.forEach(product => {
                let row = `<tr>
                    <td>${product.product_id}</td>
                    <td>${product.product_name}</td>
                    <td>${product.stock_quantity}</td>
                    <td>${product.price}</td>
                    <td>${product.order_availability ? "可" : "不可"}</td>
                </tr>`;
                table.innerHTML += row;
            });
            console.log(table);
        });
}
