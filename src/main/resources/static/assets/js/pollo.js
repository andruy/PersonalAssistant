const endpoint = "/pollo";

const btn = document.querySelector('.btn');

btn.addEventListener('click', async () => {
    btn.disabled = true;
    btn.style.cursor = 'not-allowed';

    // Show overlay
    const overlay = document.getElementById('overlay');
    overlay.style.display = 'flex';
    overlay.textContent = 'Waiting for server response...';

    collectData();

    async function collectData() {
        if (code.value.length === 16) {
            displayMessage(await sendPostRequest(endpoint));
            code.value = '';
        } else {
            displayMessage('Invalid code entered');
        }
    }

    function displayMessage(message) {
        overlay.textContent = message;
        setTimeout(() => {
            overlay.style.display = 'none';
            document.querySelectorAll('.btn').forEach(btn => {
                btn.disabled = false;
                btn.style.cursor = 'pointer';
            });
        }, 5000);
    }
});

async function sendPostRequest(endpoint) {
    const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            code: code.value,
            meal: meal.value,
            visit: visit.value
        })
    });
    const data = await response.json();

    console.log(data.report);
    return data.report;
}
