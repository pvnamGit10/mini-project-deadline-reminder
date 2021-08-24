function myDropDownFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function (e) {
    if (!e.target.matches('.dropbtn')) {
        var myDropdown = document.getElementById("myDropdown");
        if (myDropdown.classList.contains('show')) {
            myDropdown.classList.remove('show');
        }
    }
}

function showAddForm(){

    if(document.getElementById("hidden-form").style.display === "none"){
        document.getElementById("hidden-form").style.display = "block";
    }else {
        document.getElementById("hidden-form").style.display = "none";
    }
}

function lengthWord(input){
    let words = input.split(" ");
    for( let i = 0; i<words.length; i++){
        if(words[i].length>20){
            return 1;
        }
    }
    return 0;
}

function success() {
    var title = document.getElementById("todoTitle").value;
    var des = document.getElementById("todoDes").value;
    var begin = document.getElementById("todoBeginDate").value;
    var end = document.getElementById("todoEndDate").value;
    var date = new Date();
    date.setDate(date.getDate()-1)
    console.log(date)

    let flag = 0;
    if(title===""){
        document.getElementById("showError").innerText="Title must be filled in"
        flag = 1;
    }else if(title.length>50){
        document.getElementById("showError").innerText="Title limits 50 characters"
        flag = 1;
    }

    if(lengthWord(title)===1){
        document.getElementById("showError").innerText="Check your input"
        flag =1;
    }

    if(lengthWord(des)===1){
        flag = 1;
    }
    if(des.length>200){
        document.getElementById("showError").innerText="Title limits 50 characters"
        flag = 1;
    }

    if(begin>end) {
        document.getElementById("showError").innerText = "Invalid Date"
        flag = 1;
    }
    if(Date.parse(end)<date){
        flag = 1;
        document.getElementById("showError").innerText = "Check your end date"

    }

    if(begin ==="" || end ===""){
        flag =1;
    }
    if(flag===0){
        document.getElementById("showError").innerText = null;
        document.getElementById("add_btn").disabled = false;
    }else {
        document.getElementById("add_btn").disabled = true;
    }

}

function successConfirm(){
    if(document.getElementById("confirm-pwd").value === document.getElementById("pwd").value){
        document.getElementById("error-cf-pwd").innerText = null
        document.getElementById("rgs-btn").disabled = false;
    }else {
        document.getElementById("error-cf-pwd").innerText = "Confirm password does not match";
        document.getElementById("rgs-btn").disabled = true;
    }
}




