window.addEventListener("load",function(){
  i = 0;
  var app = {}
  app.device = null;
  connect();
 

  //createQuestion("What is the initialization method of an Arduino Program?","void loop()","void init()","void setup()",3);
  //createQuestion("How do you turn on the LED ?","digitalWrite(broche, HIGH)","digitalSend(HIGH, broche)","digitalWrite(broche, ON)",1);

  function connect()
	{
		evothings.arduinoble.close();

		evothings.arduinoble.connect(
			'Genuini', // Advertised name of BLE device.
			function(device)
			{
				app.device = device;
				//app.goodAnswer();
                app.startNotification();
				document.getElementById('info2').innerHTML = "Waiting for questions ...";
            },
			function(errorCode)
			{
			//	app.showMessage('Connect error: ' + errorCode + '.');
		});
		 
	};

document.addEventListener(
		'deviceready',
		function() { evothings.scriptsLoaded(app.onDeviceReady) ;
		connect();
		localStorage.setItem("connectScreen",'f');
	},
		false);



	function goodAnswer()
	{
        app.device && app.device.writeDataArray(new Uint8Array([1]), '19b10001-e8f2-537e-4f6c-d104768a1214');
		console.log("reponse envoye");
	};

	app.showMessage = function(info)
	{
		document.getElementById('info').innerHTML = info
	};

	app.startNotification = function()
	{
		app.device.enableNotification(
        '19B10001-E8F2-537E-4F6C-D104768A1214',
        function(data)
        {
            console.log('characteristic data: ' + evothings.ble.fromUtf8(data));
        },
        function(errorCode)
        {
            console.log('readCharacteristic error: ' + errorCode);
		//	waitingQuestions();
			createQuestion("What is the initialization method of an Arduino Program?","void loop()","void init()","void setup()",3);
        });
    };

	

function createQuestion(question,answer1,answer2,answer3,numberAnswerCorrect){
	u = 1;
	i = i + 1;

	var div = document.createElement('div');
	div.setAttribute('id','questionArea'+i);
	document.body.appendChild(div);

	var divquestion = document.createElement('div');
	divquestion.setAttribute('id','question');
	div.appendChild(divquestion);

	var questionAsk = document.createElement('p');
	questionAsk.setAttribute('id','info');
	divquestion.appendChild(questionAsk);

	var answer1Ask = document.createElement('button');
	answer1Ask.setAttribute('id','answer'+u);
	answer1Ask.setAttribute('class','green wide');
	div.appendChild(answer1Ask);

	u++;

	var answer2Ask = document.createElement('button');
	answer2Ask.setAttribute('id','answer'+u);
	answer2Ask.setAttribute('class','green wide');
	div.appendChild(answer2Ask);

	u++;

	var answer3Ask = document.createElement('button');
	answer3Ask.setAttribute('id','answer'+u);
	answer3Ask.setAttribute('class','green wide');
	div.appendChild(answer3Ask);

	document.getElementById('info2').style.display = "none";


	questionAsk.innerHTML = question;
	answer1Ask.innerHTML = answer1;
	answer2Ask.innerHTML = answer2;
	answer3Ask.innerHTML = answer3;

	document.getElementById('answer'+numberAnswerCorrect).onclick = function answer()
	{
		goodAnswer();
		questionAsk.style.display = "none";
		answer1Ask.style.display = "none";
		answer2Ask.style.display = "none";
		answer3Ask.style.display = "none";
		waitingQuestions();
	};
	};

	function waitingQuestions(){
	if(0){ //data not received
		document.getElementById('info2').style.display = "inline-block";
		document.getElementById('info2').innerHTML = "Waiting for questions...";
	}else{
		createQuestion("How do you turn on the LED ?","digitalWrite(broche, HIGH)","digitalSend(HIGH, broche)","digitalWrite(broche, ON)",1);
	}
	//setTimeout(waitingQuestions,3000);
	}
});





	
	



