const queryString = window.location.search;
const URLParams = new URLSearchParams(queryString);
const email = URLParams.get("email");
const host = URLParams.get("host");
const friendTbody = document.getElementById("friends-tbody");
const requestTbody = document.getElementById("received-friend-requests-tbody");
const sentTbody = document.getElementById("sent-friend-requests-tbody");
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


fetch("/users/" + email)
    .then(response => response.json())
    .then(stuff)

function stuff(user) {
    document.getElementById("user-info").innerHTML = `
    <p>${user.name}<p>
    <p>${user.email}<p>
    `;
    console.log(user);

    user.friends.map(fillFriendTable);
    user.receivedRequests.map(fillReceivedFriendTable);
    user.sentFriendRequests.map(fillSentRequestTable);


}

function fillFriendTable(friend) {
    const friendsRow = document.createElement("tr");

    friendsRow.innerHTML = `
<td>
    <p>${friend.email}</p>
    </td>
    <td>
    <p>${friend.host}</p>
</td>
    `;

    friendTbody.appendChild(friendsRow);
}

function fillReceivedFriendTable(receivedRequest) {
    const requestRow = document.createElement("tr");

    requestRow.innerHTML = `
    <td>
    <p>${receivedRequest.externalEmail}</p>
    </td>
    <td>
    <p>${receivedRequest.externalHost}</p>
</td>
    `;

    requestTbody.appendChild(requestRow);
}

function fillSentRequestTable(sentRequest) {
    const requestRow = document.createElement("tr");

    requestRow.innerHTML = `
     <td>
    <p>${sentRequest.externalEmail}</p>
    </td>
    <td>
    <p>${sentRequest.externalHost}</p>
</td>
    `;

    sentTbody.appendChild(requestRow);
}
