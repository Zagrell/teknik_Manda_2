const queryString = window.location.search;
const URLParams = new URLSearchParams(queryString);
const email = URLParams.get("email");
const host = URLParams.get("host");
document.getElementById("src-email").value = email;
document.getElementById("src-host").value = host;

/*
document.getElementById("request-button").addEventListener("click", () => {
   const destEmail = document.getElementById("dest-email").value;
   const destHost = document.getElementById("dest-host").value;
   let method = document.getElementById("method");
   method = method.options[method.selectedIndex].value;





   fetch(destHost + "/friendship",{
       method : "POST",
       headers : {
           "Content-type": "application/json; charset=UTF-8"
       },
       body : method + " " + email + " " + host + " " + destEmail + " " + destHost + " " + 1 + "\n\l"
   })


});
 */


fetch("/users/"+email)
    .then(response => response.json())
    .then(stuff)

function stuff(user) {
    document.getElementById("user-info").innerHTML = `
    <p>${user.name}<p>
    <p>${user.email}<p>
    `;

    user.friends.map(fillFriendTable);


}

function fillFriendTable() {

}
