const tbody = document.getElementById("users-tbody");

fetch("/host")
    .then(async result => {
        document.getElementById("host").innerText = await result.text();
    })

fetch("/users")
    .then(response => response.json())
    .then(results => results.map(createTableRow));



document.getElementById("add-button").addEventListener("click", () => {

    const user = {
        email : document.getElementById("add-email").value,
        email : document.getElementById("add-email").value,
        name : document.getElementById("add-name").value
    };

    fetch("/users",{
        method : "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(user)
    });

    console.log("hej");

});

function createTableRow(user) {
    console.log(user);

    const tr = document.createElement("tr");

    const a = document.createElement("a");

    a.href = "../html/user.html?email=" + user.email + "&host=" + user.host;


    const emailTd = document.createElement("td");
    const nameTd = document.createElement("td");

    emailTd.innerText = user.email;
    nameTd.innerText = user.name;


    tr.appendChild(emailTd);
    tr.appendChild(nameTd);
    a.appendChild(tr);
    tbody.appendChild(a);

}
