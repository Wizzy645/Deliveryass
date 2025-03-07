async function fetchVehicleDetails() {
    const urlParams = new URLSearchParams(window.location.search);
    const plateNumber = urlParams.get('plateNumber');
    try {
        const response = await fetch(`http://localhost:8080/api/v1/vehicle/get-vehicle/${plateNumber}`);
        if (response.ok) {
            const vehicle = await response.json();
            displayVehicleDetails(vehicle);
        } else {
            console.error('Vehicle not found');
            document.getElementById('vehicleDetails').innerHTML = '<p>Vehicle not found.</p>';
        }
    } catch (error) {
        console.error('Error fetching vehicle:', error);
        document.getElementById('vehicleDetails').innerHTML = '<p>Error fetching vehicle details.</p>';
    }
}

function displayVehicleDetails(vehicle) {
    document.getElementById('vehicleName').innerText = `${vehicle.name} (${vehicle.plateNumber})`;
    const detailsContainer = document.getElementById('vehicleDetails');
    detailsContainer.innerHTML = `
        <p><strong>Status:</strong> ${vehicle.status || 'Unknown'}</p>
        <p><strong>Type:</strong> ${vehicle.type || 'N/A'}</p>
        <p><strong>Fuel Capacity:</strong> ${vehicle.fuelCapacity} L</p>
        <p><strong>Carrying Weight:</strong> ${vehicle.carryingWeight} kg</p>
        <h5>Items:</h5>
        <ul>
            ${vehicle.items.map(item => `<li>${item.name} (Code: ${item.code}, Weight: ${item.weight} kg)</li>`).join('')}
        </ul>
    `;

    // Set up the delete button
    document.getElementById('deleteButton').onclick = () => deleteVehicle(vehicle.id);
}

async function deleteVehicle(vehicleId) {
    if (!vehicleId) {
        console.error("Error: Missing vehicle ID.");
        return;
    }

    // Split the vehicleId if it contains a colon
    const idParts = vehicleId.split(':');
    const actualId = idParts[0]; // Assuming the first part is the actual ID

    if (confirm('Are you sure you want to delete this vehicle?')) {
        console.log("Attempting to delete vehicle with ID:", actualId);

        try {
            const response = await fetch(`http://localhost:8080/api/v1/vehicle/${actualId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Vehicle deleted successfully.');
                window.location.href = 'index.html';
            } else {
                console.error('Failed to delete vehicle:', response.status, response.statusText);
            }
        } catch (error) {
            console.error('Error deleting vehicle:', error);
        }
    }
} // Closing brace for deleteVehicle function

// Initialize
document.addEventListener('DOMContentLoaded', fetchVehicleDetails);