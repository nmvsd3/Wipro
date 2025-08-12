
function updateClock() {
    document.getElementById("clock").innerText = new Date().toLocaleTimeString();
}
setInterval(updateClock, 1000);
updateClock();


const start = new Date("2025-08-01T09:00:00");
const end = new Date("2025-08-01T17:30:00");
const diffHours = (end - start) / (1000 * 60 * 60);
document.getElementById("workHours").innerText =
    `Congratulations! You have worked: ${diffHours} hours`;

function makeCounter() {
    let count = 0;
    return function () {
        count++;
        return count;
    };
}
const visitCounter = makeCounter();
document.getElementById("visitCount").innerText =
    `You have visited this site ${visitCounter()} time(s)`;


function shoppingCart() {
    let items = 0;
    return {
        add: function () { items++; return items; },
        remove: function () { if (items > 0) items--; return items; },
        getTotal: function () { return items; }
    };
}
const cart = shoppingCart();
document.getElementById("addItem").onclick = () => {
    document.getElementById("cartInfo").innerText = `Items in cart: ${cart.add()}`;
};
document.getElementById("removeItem").onclick = () => {
    document.getElementById("cartInfo").innerText = `Items in cart: ${cart.remove()}`;
};


function todoList() {
    let tasks = [];
    return {
        add: function (task) { tasks.push(task); return tasks; },
        remove: function (index) { tasks.splice(index, 1); return tasks; },
        get: function () { return tasks; }
    };
}
const todos = todoList();

document.getElementById("addTask").onclick = () => {
    let task = document.getElementById("todoInput").value;
    if (task.trim() !== "") {
        todos.add(task);
        renderTodos();
        document.getElementById("todoInput").value = "";
    }
};

function renderTodos() {
    let list = document.getElementById("todoList");
    list.innerHTML = "";
    todos.get().forEach((t, i) => {
        let li = document.createElement("li");
        li.textContent = t;
        let btn = document.createElement("button");
        btn.textContent = "remove";
        btn.onclick = () => { todos.remove(i); renderTodos(); };
        li.appendChild(btn);
        list.appendChild(li);
    });
}


const add = (a, b) => a + b;
const subtract = (a, b) => a - b;
const multiply = (a, b) => a * b;

document.getElementById("calcBtn").onclick = () => {
    const n1 = parseFloat(document.getElementById("num1").value);
    const n2 = parseFloat(document.getElementById("num2").value);
    const op = document.getElementById("operation").value;
    let result;
    if (op === "+") result = add(n1, n2);
    else if (op === "-") result = subtract(n1, n2);
    else if (op === "*") result = multiply(n1, n2);
    document.getElementById("calcResult").innerText = `Result: ${result}`;
};
