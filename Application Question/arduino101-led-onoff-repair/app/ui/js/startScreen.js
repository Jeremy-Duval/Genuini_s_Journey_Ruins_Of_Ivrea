
// Application object.
	var app = {}

	// Connected device.
	app.device = null;

    //display or not connect screen
    connectScreen = true;

    
	app.coursScreen = function()
	{
		window.location = "courses.html";
	}


	app.screenTest = function()
	{
		app.device && app.device.writeDataArray(new Uint8Array([1]), '19b10001-e8f2-537e-4f6c-d104768a1214');
	}

	app.enterGame = function()
	{
		app.device && app.device.writeDataArray(new Uint8Array([0]), '19b10001-e8f2-537e-4f6c-d104768a1214');
	}

	app.showMessage = function(info)
	{
		document.getElementById('info').innerHTML = info
	};

	// Called when BLE and other native functions are available.
	app.onDeviceReady = function()
	{
		app.showMessage('Touch the connect button to begin.');
        testlocal();  
	};

	app.connect = function()
	{
		evothings.arduinoble.close();
		
		app.showMessage('Connecting...');

		evothings.arduinoble.connect(
			'Genuini', // Advertised name of BLE device.
			function(device)
			{
				app.device = device;
				app.showMessage('Connected! Test the LCD screen.');
                document.getElementById('Connect').disabled = true;
		        document.getElementById('Connect').innerHTML = "Already connected";
                document.getElementById('enterGame').innerHTML = "Please test the screen";
			},
			function(errorCode)
			{
				app.showMessage('Connect error: ' + errorCode + '.');
			});
	};

    app.questionScreen = function(){
        window.location = "questionScreen.html";
    }

 /*   app.questionScreen = function(){
        window.location("waitingScreen.html");
    } */

	document.addEventListener(
		'deviceready',
		function() { evothings.scriptsLoaded(app.onDeviceReady) },
		false);

    var testScreen = document.getElementById('testScreen');
    var enterGame = document.getElementById('enterGame');

	testScreen.disabled = true;
	testScreen.innerHTML = "Please connect first";
	enterGame.disabled = true;
	enterGame.innerHTML = "Please connect first";
	document.getElementById('Connect').addEventListener('click', function(){
		testScreen.disabled = false;
		testScreen.innerHTML = "Screen Test";
	});

    testScreen.addEventListener('click', function(){
		testScreen.disabled = true;
		testScreen.innerHTML = "Already Tested";
        document.getElementById('info').innerHTML = "Let's go";
        enterGame.disabled = false;
	    enterGame.innerHTML = "Let's Play";
        enterGame.addEventListener('click',function(){
            
            addMenu();
         });
    });

    function addMenu(){
        document.getElementById('Connect').style.display = "none";
        testScreen.style.display = "none";
        enterGame.style.display = "none";
        for(var i = 0; i < 3; i++){
        var br = document.createElement("br");
        document.getElementById('br').appendChild(br);
        }
        document.getElementById('courses').style.display = "inline";
        document.getElementById('genuini_duck').style.display = "inline";
        document.getElementById('questions').style.display = "inline";
        document.getElementById('genuini_front').style.display = "inline";
        document.getElementById('info').style.display = "none";
        document.getElementById('title').innerHTML = "Select your part";      
    }

    
   function testlocal(){
        if(localStorage.getItem('connectScreen') === "f"){
            connectScreen = false;
         }else{
             connectScreen = true;
         }

        if(!connectScreen){
            addMenu();
        }
    }

  function quitApp(){
      localStorage.setItem("connectScreen",'m');
      navigator.app.exitApp();
  }  

        