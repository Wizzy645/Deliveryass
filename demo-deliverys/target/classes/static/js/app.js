// Modal Functions
function showVehicleForm() {
    new bootstrap.Modal(document.getElementById('vehicleModal')).show();
}

function showItemForm() {
    new bootstrap.Modal(document.getElementById('itemModal')).show();
}

// Function to redirect to vehicle details page
function redirectToVehicleDetails() {
    const plateNumber = document.getElementById('searchPlateNumber').value.trim();
    if (plateNumber) {
        window.location.href = `vehicle-details.html?plateNumber=${plateNumber}`;
    } else {
        alert("Please enter a plate number.");
    }
}

// Vehicle Functions
async function loadVehicles() {
    try {
        const response = await fetch('/api/v1/vehicle'); // Correct endpoint
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const vehicles = await response.json();
        if (Array.isArray(vehicles)) {
            renderVehicles(vehicles);
        } else {
            console.error('Expected an array of vehicles, but got:', vehicles);
        }
    } catch (error) {
        console.error('Error loading vehicles:', error);
    }
}

function renderVehicles(vehicles) {
    const container = document.getElementById('vehicles');
    container.innerHTML = vehicles.map(vehicle => `
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${vehicle.name}</h5>
                    <p class="card-text">
                        <strong>ID:</strong> ${vehicle.id}<br>
                        <strong>Plate Number:</strong> ${vehicle.plateNumber}<br>
                        <strong>Status:</strong> ${vehicle.status}<br>
                        <strong>Type:</strong> ${vehicle.type}<br>
                    </p>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-sm btn-outline-primary"
                            onclick="window.location.href='vehicle-details.html?plateNumber=${vehicle.plateNumber}'">
                            Details
                        </button>\n

                        <button class="btn btn-sm btn-outline-danger" onclick="deleteVehicle(${vehicle.id})">
                            Delete
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `).join('');
}

// Item Functions
async function loadItems() {
    try {
        const response = await fetch('/api/v1/item');
        if (!response.ok) {
            const errorText = await response.text(); // Log the raw response
            throw new Error(`Network response was not ok: ${errorText}`);
        }
        const items = await response.json();
        if (Array.isArray(items)) {
            renderItems(items);
        } else {
            console.error('Expected an array of items, but got:', items);
        }
    } catch (error) {
        console.error('Error loading items:', error);
    }
}
// Form Handlers
document.getElementById('vehicleForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = {
        name: document.querySelector('#vehicleForm input[name="name"]').value,
        plateNumber: document.querySelector('#vehicleForm input[name="plateNumber"]').value,
        status: document.querySelector('#vehicleForm input[name="status"]').value,
        type: document.querySelector('#vehicleForm input[name="type"]').value,
        fuelCapacity: parseFloat(document.querySelector('#vehicleForm input[name="fuelCapacity"]').value),
        carryingWeight: parseFloat(document.querySelector('#vehicleForm input[name="carryingWeight"]').value),
        items: [] // Initialize with an empty array or handle items as needed
    };
    try {
        const response = await fetch('/api/v1/vehicle/create_vehicle', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });
        if (response.ok) {
            location.reload(); // Reload the page to see the new vehicle
        } else {
            console.error('Failed to create vehicle:', response.statusText);
        }
    } catch (error) {
        console.error('Error:', error);
    }
});

function renderItems(items) {
    const container = document.getElementById('itemsList');
    container.innerHTML = items.map(item => `
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${item.name}</h5>
                    <p class="card-text">
                        <strong>ID:</strong> ${item.id}<br>
                        <strong>Code:</strong> ${item.code}<br>
                        <strong>Weight:</strong> ${item.weight} kg
                    </p>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteItem(${item.id})">
                        Delete
                    </button>
                </div>
            </div>
        </div>
    `).join('');
}



document.getElementById('itemForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = {
        name: document.querySelector('#itemForm input[name="name"]').value,
        weight: parseFloat(document.querySelector('#itemForm input[name="weight"]').value),
        code: `ITEM-${Math.floor(Math.random() * 1000)}` // Generate a random code
    };
    try {
        const response = await fetch('/api/v1/item/create-items', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });
        if (response.ok) {
            location.reload(); // Reload the page to see the new item
        } else {
            console.error('Failed to create item:', response.statusText);
        }
    } catch (error) {
        console.error('Error:', error);
    }
});

//async function addItemToVehicle(event) {
//    event.preventDefault();
//
//    const vehicleId = document.getElementById('vehicleId').value;
//    const itemId = document.getElementById('itemId').value;
//
//    try {
//        const response = await fetch(`/api/v1/vehicle/${vehicleId}/add-item/${itemId}`, {
//            method: 'POST',
//            headers: { 'Content-Type': 'application/json' }
//        });
//
//        const responseMessage = document.querySelector('.response-message');
//        if (response.ok) {
//            responseMessage.textContent = 'Item added to vehicle successfully!';
//            responseMessage.classList.remove('error');
//            responseMessage.classList.add('success');
//        } else {
//            const errorText = await response.text();
//            responseMessage.textContent = `Error: ${errorText}`;
//            responseMessage.classList.remove('success');
//            responseMessage.classList.add('error');
//        }
//    } catch (error) {
//        console.error('Error:', error);
//        const responseMessage = document.querySelector('.response-message');
//        responseMessage.textContent = 'An error occurred. Please try again.';
//        responseMessage.classList.remove('success');
//        responseMessage.classList.add('error');
//    }
//}

// Delete Vehicle Function
async function deleteVehicle(vehicleId) {
    if (confirm('Are you sure you want to delete this vehicle?')) {
        try {
            const response = await fetch(`/api/v1/vehicle/${vehicleId}`, { // Updated URL
                method: 'DELETE'
            });
            if (response.ok) {
                alert('Vehicle deleted successfully.');
                location.reload();
            } else {
                const errorText = await response.text(); // Log the raw response
                console.error('Failed to delete vehicle:', errorText);
            }
        } catch (error) {
            console.error('Error deleting vehicle:', error);
        }
    }
}

async function deleteItem(itemId) {
    if (confirm('Are you sure you want to delete this item?')) {
        const url = `/api/v1/item/${itemId}`;
        console.log(`Deleting item: ${url}`);
        try {
            const response = await fetch(url, { method: 'DELETE' });
            if (response.ok) {
                alert('Item deleted successfully.');
                location.reload();
            } else if (response.status === 404) {
                alert('Item not found.');
            } else {
                const errorText = await response.text(); // Log the raw response
                console.error('Failed to delete item:', errorText);
                alert('Failed to delete item. Please try again.');
            }
        } catch (error) {
            console.error('Error deleting item:', error);
            alert('An error occurred while deleting the item.');
        }
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadVehicles();
    loadItems();
});