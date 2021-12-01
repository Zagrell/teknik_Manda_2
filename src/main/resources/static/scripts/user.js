const queryString = window.location.search;
const URLParams = new URLSearchParams(queryString);
const email = URLParams.get("email");
const host = URLParams.get("host");

document.getElementById("request-button").addEventListener("click", () => {
   const destEmail = document.getElementById("dest-email").value;
   const destHost = document.getElementById("dest-host").value;
   let method = document.getElementById("method");
   method = method.options[method.selectedIndex].value;





   fetch(destHost + "/",{
       method : "POST",
       headers : {
           "Content-type": "application/json; charset=UTF-8"
       },
       body : method + " " +
   })


});

fetch("/users/"+email)
    .then(response => response.json())
    .then(stuff)

function stuff(user) {
    document.getElementById("user-info").innerHTML = `
    <p>${user.name}<p>
    <p>${user.email}<p>
    `;

    user.friends.map(fillFriendTable);
    user.map()
    const hej = document.createElement("td");


}

function fillFriendTable() {

}
