/**
 * @author Darth Frazier
 */
var credentials = {
			"email" : "kmjf@princeton.edu",
			"github" : "https://github.com/darthfrazier"
			}
			var token;
			var jsontoken;
			var reversed;
				
		$(document).ready(function(){
			
			$.ajax({
                url: "http://challenge.code2040.org/api/register",
                type: 'POST',
                crossDomain: true,
                dataType: 'text',
                data: JSON.stringify(credentials)
            }).done(function (data) {
                
                obj = JSON.parse(data);
                token = obj.result;
                document.getElementById('token').value = token;
                jsontoken = {
				"token": token
					};
               
            }); 
		});
			
			
			
			
		$(c1).click(function() {
			console.log(token);
			$.ajax({
                url: "http://challenge.code2040.org/api/getstring",
                type: 'POST',
                crossDomain: true,
                dataType: 'text',
                data: JSON.stringify(jsontoken)
            }).done(function (data) {
                
                obj = JSON.parse(data);
                string = obj.result;
                document.getElementById('c1input').value = string;
                reversed = string.split("").reverse().join("");
                document.getElementById('c1output').value = reversed;
                submit1(reversed);
               
            });
        });
          
          
        $(c2).click(function() {
			console.log(token);
			$.ajax({
                url: "http://challenge.code2040.org/api/haystack",
                type: 'POST',
                crossDomain: true,
                dataType: 'text',
                data: JSON.stringify(jsontoken)
            }).done(function (data) {
                
                obj = JSON.parse(data);
                needle = obj.result.needle;
                haystack = _(obj.result.haystack).toArray();
                document.getElementById('c2input').value = needle;
                index = haystack.indexOf(needle);
                document.getElementById('c2output').value = index;
                submit2(index);
               
            });
        });  
        
        $(c3).click(function() {
			console.log(token);
			$.ajax({
                url: "http://challenge.code2040.org/api/prefix",
                type: 'POST',
                crossDomain: true,
                dataType: 'text',
                data: JSON.stringify(jsontoken)
            }).done(function (data) {
                
                obj = JSON.parse(data);
               	prefix = obj.result.prefix;
                array = _(obj.result.array).toArray();
                var noprefix = [];
                document.getElementById('c3input').value = prefix;
                for (var i = 0; i < array.length; i++) {
                	if(array[i].lastIndexOf(prefix, 0) === -1) {
                		noprefix.push(array[i]);
                	}
                }
                document.getElementById('c3output').value = noprefix.length;
                submit3(noprefix);
               
            });
        });  
        
        $(c4).click(function() {
			console.log(token);
			$.ajax({
                url: "http://challenge.code2040.org/api/time",
                type: 'POST',
                crossDomain: true,
                dataType: 'text',
                data: JSON.stringify(jsontoken)
            }).done(function (data) {
                
                obj = JSON.parse(data);
               	datestamp = obj.result.datestamp;
               	seconds = obj.result.interval;
                document.getElementById('c4input').value = datestamp;
                dp = new Date(datestamp);
                newseconds = dp.getSeconds() + seconds;
                dp.setSeconds(newseconds);
                newdatestamp = dp.toISOString();
                document.getElementById('c4output').value = newdatestamp;
                submit4(newdatestamp);
               
            });
        });  
         function submit1 (string) {  
            $.ajax({
            	url:"http://challenge.code2040.org/api/validatestring",
            	type: 'POST',
            	crossDomain: true,
            	dataType: 'text',
            	data: JSON.stringify({"token":token,"string":string})
            }).done(function (data) {
            	console.log(data);
            });
            
          }
         
         function submit2 (index) {  
            $.ajax({
            	url:"http://challenge.code2040.org/api/validateneedle",
            	type: 'POST',
            	crossDomain: true,
            	dataType: 'text',
            	data: JSON.stringify({"token":token,"needle":index})
            }).done(function (data) {
            	console.log(data);
            });
            
          }
          
          function submit3 (array) {  
            $.ajax({
            	url:"http://challenge.code2040.org/api/validateprefix",
            	type: 'POST',
            	crossDomain: true,
            	dataType: 'text',
            	data: JSON.stringify({"token":token,"array":array})
            }).done(function (data) {
            	console.log(data);
            });
            
          }
          
          function submit4 (newdatestamp) {  
            $.ajax({
            	url:"http://challenge.code2040.org/api/validatetime",
            	type: 'POST',
            	crossDomain: true,
            	dataType: 'text',
            	data: JSON.stringify({"token":token,"datestamp":newdatestamp})
            }).done(function (data) {
            	console.log(data);
            });
            
          }
         
		
			