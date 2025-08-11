let menu = [
    { item: "Burger", price: 150 },
    { item: "Pizza", price: 250 },
    { item: "Pasta", price: 200 },
    { item: "Fries", price: 80 }
];

console.log("=== MENU ===");
menu.forEach(m => console.log(`${m.item} - ₹${m.price}`));

let customerOrder = ["Burger", "Pizza", "Fries"];
let validOrder = [];

customerOrder.forEach(orderItem => {
    let menuItem = menu.find(m => m.item.toLowerCase() === orderItem.toLowerCase());
    if (menuItem) {
        validOrder.push(menuItem);
    } else {
        console.log(`Sorry, ${orderItem} is not available.`);
    }
});

let total = 0;
validOrder.forEach(item => total += item.price);

let discount = 0;
if (total > 500) {
    discount = total * 0.1;
    total -= discount;
}

console.log("\n=== Order Summary ===");
validOrder.forEach(item => console.log(`${item.item} - ₹${item.price}`));
if (discount > 0) console.log(`Discount Applied: ₹${discount}`);
console.log(`Total Amount: ₹${total}`);
