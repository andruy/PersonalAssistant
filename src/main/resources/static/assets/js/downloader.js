let listItems = [];
const endpointUrl = "/alteryt";

function addToList() {
    let input = document.getElementById("textInput");
    let text = input.value.trim();

    if (text !== "") {
        let list = document.getElementById("textList");
        let listItem = document.createElement("li");
        listItem.appendChild(document.createTextNode(text));
        list.appendChild(listItem);
        input.value = ""; // Clear the text input
        listItems.push(text);

        // Apply fade-in animation to the new list item
        setTimeout(function () {
            listItem.classList.add("fade-in");
        }, 10);
    }
}

async function sendList() {
    if (listItems.length === 0) {
        alert("The list is empty. Please add items to the list first.");
        return;
    }

    let data = {
        list: listItems
    };

    // Sending the list with AJAX call
    let response = await fetch(endpointUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });
    if (response.status === 200) {
        let result = await response.json();
        console.log(result);
        clearList(result[1]);
    } else {
        alert("Something went wrong");
    }
}

function clearList(msg) {
    listItems = []; // Clear the array list

    // Clear the items list with fade-out animation
    let list = document.getElementById("textList");
    let listItemsToClear = list.getElementsByTagName("li");
    for (let i = 0; i < listItemsToClear.length; i++) {
        listItemsToClear[i].classList.remove("fade-in");
        listItemsToClear[i].classList.add("fade-out");
    }

    // Remove list items after fade-out animation completes
    setTimeout(function () {
        list.innerHTML = "";

        // Add message as a list item
        let messageItem = document.createElement("li");
        messageItem.appendChild(document.createTextNode(msg));
        list.appendChild(messageItem);
        messageItem.classList.add("fade-in");

        // Remove the message item after 3 seconds
        setTimeout(function () {
            messageItem.classList.remove("fade-in");
            messageItem.classList.add("fade-out");
            setTimeout(function () {
                list.removeChild(messageItem);
            }, 300);
        }, 3000);
    }, 300);
}
