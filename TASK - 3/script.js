const inputBox = document.getElementById("input-box");
const taskContainer = document.getElementById("task-container");

function addTask() {
    if(inputBox.value === '') {
        alert("You must write some task!");
    }
    else {
        let li = document.createElement("li");
        li.innerHTML = inputBox.value;
        taskContainer.appendChild(li);

        let span = document.createElement("span");
        span.innerHTML = "\u00d7";//"X" : cross button
        li.appendChild(span);
    }
    inputBox.value = "";
    saveData();
}

taskContainer.addEventListener("click", function(e) {
    if(e.target.tagName === "LI") {
        e.target.classList.toggle("checked");
        saveData();
    }
    else if(e.target.tagName === "SPAN") {
        e.target.parentElement.remove();
        saveData();
    }
}, false);

function saveData() {
    localStorage.setItem("data", taskContainer.innerHTML);
}
function getAndShowData() {
    taskContainer.innerHTML = localStorage.getItem("data");
}
getAndShowData();