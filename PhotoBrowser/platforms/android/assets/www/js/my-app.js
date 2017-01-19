// Initialize your app
var myApp = new Framework7({
    animateNavBackIcon:true
});

// Export selectors engine
var $$ = Dom7;

document.getElementById('backButton').style.display = 'none';
         
document.getElementById('smallImage').style.display = 'none';

var myPhotos = myApp.photoBrowser({

	photos : [
	
		'https://farm9.staticflickr.com/8645/16031539611_b45d2bbe57_z.jpg',
		'https://farm4.staticflickr.com/3933/15296234239_4f11d889a8_z.jpg',
		'https://farm3.staticflickr.com/2949/15296430490_3366e6f050_z.jpg',
		'https://farm4.staticflickr.com/3937/15483139575_c6eced4510_z.jpg'
	],
	theme : 'dark'

});

$$('.galleryView').on('click', function(){

	myPhotos.open();

});


function capturePhoto(){

        // Take picture from the camera and retrieve the image as  base 64 encoded string
        navigator.camera.getPicture(onPhotoSuccess,onFail,{quality:50,destinationType: Camera.DestinationType.DATA_URL});

        }

        function onPhotoSuccess(imageData){
         
         var smallImage = document.getElementById('smallImage');
         smallImage.style.display = 'block';
         smallImage.src = "data:image/jpeg;base64," + imageData; 
         alert('Success');
  
         document.getElementById('gallery').style.display = 'none';
         
         document.getElementById('takePicture').style.display = 'none';
         
         document.getElementById('photoTitle').style.display = 'none';
         
         document.getElementById('backButton').style.display = 'block';
         
		document.getElementById('smallImage').style.display = 'block';
         
         
    }
    
    function showPics(){
    
    
    	document.getElementById('gallery').style.display = 'block';
         
         document.getElementById('takePicture').style.display = 'block';
         
         document.getElementById('photoTitle').style.display = 'block';
         
         document.getElementById('backButton').style.display = 'none';
         
		document.getElementById('smallImage').style.display = 'none';
    	
    
    }
      
         
         function onFail(message){
          
          	alert('Failed because: ' + message);
          
          }


// Add main View
var mainView = myApp.addView('.view-main', {
    // Enable dynamic Navbar
    dynamicNavbar: true,
    // Enable Dom Cache so we can use all inline pages
    domCache: true
});
