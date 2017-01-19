// Initialize your app
var myApp = new Framework7();
var xmlHttp = new XMLHttpRequest();

// Export selectors engine
var $$ = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});

// Callbacks to run specific code for specific pages, for example for About page:
myApp.onPageInit('about', function (page) {
    // run createContentPage func after link was clicked
    $$('.create-page').on('click', function () {
        createContentPage();
    });
});
/*function getForm() {
    xmlHttp.onreadystatechange = function(){
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            document.getElementById('try').innerHTML = xmlHttp.responseText;
        }
    }
}*/

// Generate dynamic page
var dynamicPageIndex = 0;
function createContentPage() {
	mainView.router.loadContent(
        '<!-- Top Navbar-->' +
        '<div class="navbar">' +
        '  <div class="navbar-inner">' +
        '    <div class="left"><a href="#" class="back link"><i class="icon icon-back"></i><span>Back</span></a></div>' +
        '    <div class="center sliding">Dynamic Page ' + (++dynamicPageIndex) + '</div>' +
        '  </div>' +
        '</div>' +
        '<div class="pages">' +
        '  <!-- Page, data-page contains page name-->' +
        '  <div data-page="dynamic-pages" class="page">' +
        '    <!-- Scrollable page content-->' +
        '    <div class="page-content">' +
        '      <div class="content-block">' +
        '        <div class="content-block-inner">' +
        '          <p>Here is a dynamic page created on ' + new Date() + ' !</p>' +
        '          <p>Go <a href="#" class="back">back</a> or go to <a href="services.html">Services</a>.</p>' +
        '        </div>' +
        '      </div>' +
        '    </div>' +
        '  </div>' +
        '</div>'
    );
	return;
}

function GetPicture() {
    
    navigator.camera.getPicture(onPhotoSuccess,onFail,{quality: 50,
                                destinationType: Camera.DestinationType.FILE_URI,sourceType: Camera.PictureSourceType.PHOTOLIBRARY});
    

}

function onPhotoSuccess(imageData) {
    
    var picture = document.getElementById('image');
    if (imageData!=null && imageData!="") {
        /*var photoSplit = imageData.split("%3A")
        var imageURL = "content://media/external/images/media/"+photoSplit[1];*/
        picture.value=imageData;
    }
    
    
 
 }

function onFail(message) {
    alert('Failed: ' + message);
}

function SaveContact() {
    var contact = navigator.contacts.create();
    var name = new ContactName();
    var phoneNumbers = [];
    var photo = [];
    var email = [];
    var weburl= [];
    var birthDate = [];

    var nameattr  = document.getElementById('name').value.split(" ");
    if (document.getElementById('name').value != "" && document.getElementById('name').value != null) {
         contact.displayName = document.getElementById('name').value;
         contact.nickname = nameattr[0];
    }
    else
    alert('Enter Name')
    phoneNumbers[0] = new ContactField('work',document.getElementById('work').value,false);
    phoneNumbers[1] = new ContactField('home',document.getElementById('home').value,false);
    phoneNumbers[2] = new ContactField('mobile',document.getElementById('mobile').value,true);
    if(document.getElementById('image').value != "" && document.getElementById('image').value != null){
    photo[0] = new ContactField('photo',document.getElementById('image').value,true);
    contact.photos = photo;}
    email[0] = new ContactField('email',document.getElementById('email').value,true);
    weburl[0] = new ContactField('home page', document.getElementById('url').value, true);
    birthDate[0] = new ContactField('note',document.getElementById('birthdate').value,true);
    contact.urls = weburl;
    contact.phoneNumbers = phoneNumbers;
    
    contact.emails = email;
    contact.notes = birthDate;
    contact.save(onSuccess,onError);
    
 }

function onSuccess(contact) {
    alert("Save Success");
}

function onError(contactError) {
    alert("Error = " + contactError.code);
}   
