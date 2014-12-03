__author__ = 'Darthfrazier'




import requests
import json
import datetime


#------------------------Register-----------------------------#

login = {"email":"kmjf@princeton.edu","github":"https://github.com/darthfrazier"}
login = json.dumps(login)
r = requests.post("http://challenge.code2040.org/api/register", data=login)
stringtoken = json.loads(r.text)
stringtoken = stringtoken["result"]
print("Your token is " + stringtoken + "\n")

#--------------------------Challenge One------------------------#
print("Running Challenge One\n")


token = {"token":stringtoken}
token = json.dumps(token)
r = requests.post("http://challenge.code2040.org/api/getstring", data=token)
string = json.loads(r.text)
print("Input string is " + string["result"])
outputstring = string["result"][::-1]
print("Reversed string is " + outputstring)

jsonstring = {"token":stringtoken, "string": outputstring}
jsonstring = json.dumps(jsonstring)
r = requests.post("http://challenge.code2040.org/api/validatestring", data=jsonstring)
results = json.loads(r.text)
results = results["result"]
print("Did I pass the test? \n" + results)
if results == "PASS: stage1. Enrollment record updated!":
    print("Congratulations, on to the next one\n")
else:
    print("Uh oh, there may be something wrong with the code\n")

#-----------------------------Challenge Two---------------------------#
print("Running Challenge Two\n")


r = requests.post("http://challenge.code2040.org/api/haystack", data=token)
jsonresult = json.loads(r.text)
result = jsonresult["result"]
needle = result["needle"]
haystack = result["haystack"]

print("Needle is " + needle)
print("Haystack is ")
for i  in range (0,len(haystack)):
    print(haystack[i])

index = haystack.index(needle)

jsonstring = {"token":stringtoken, "needle": index}
jsonstring = json.dumps(jsonstring)
r = requests.post("http://challenge.code2040.org/api/validateneedle", data=jsonstring)
results = json.loads(r.text)
results = results["result"]
print("Did I pass the test? \n" + results)
if results == "PASS: stage2. Enrollment record updated!":
    print("Congratulations, on to the next one\n")
else:
    print("Uh oh, there may be something wrong with the code\n")

#------------------------------Challenge Three------------------------#

print("Running Challenge Prefix\n")


r = requests.post("http://challenge.code2040.org/api/prefix", data=token)
jsonresult = json.loads(r.text)
result = jsonresult["result"]
prefix = result["prefix"]
array = result["array"]
temp = []


print("Prefix is " + prefix)
print("Array is ")
for i in range(0,len(array)):
    print(array[i])


for j in range(0, len(array)):
    if array[j].startswith(prefix) == False:
        temp.append(array[j])
len = len(temp)
print(len)

jsonstring = {"token":stringtoken, "array": temp}
jsonstring = json.dumps(jsonstring)
r = requests.post("http://challenge.code2040.org/api/validateprefix", data=jsonstring)
results = json.loads(r.text)
results = results["result"]
print("Did I pass the test? \n" + results)
if results == "PASS: stage3. Enrollment record updated!":
    print("Congratulations, on to the next one\n")
else:
    print("Uh oh, there may be something wrong with the code\n")

#---------------------------Challenge Four---------------------------#

print("Running Challenge Four\n")


r = requests.post("http://challenge.code2040.org/api/time", data=token)
jsonresult = json.loads(r.text)
result = jsonresult["result"]
date = result["datestamp"]
interval = result["interval"]

print("Datestamp is " + date)

datestamp = datetime.datetime.strptime(date, "%Y-%m-%dT%H:%M:%S.%fZ")
datestampplus = datestamp + datetime.timedelta(0,interval)
datestampplus = datestampplus.isoformat()

print("New datestamp is " + datestampplus)


jsonstring = {"token":stringtoken, "datestamp": datestampplus}
jsonstring = json.dumps(jsonstring)
r = requests.post("http://challenge.code2040.org/api/validatetime", data=jsonstring)
results = json.loads(r.text)
results = results["result"]
print("Did I pass the test? \n" + results)
if results == "PASS: stage4. Enrollment record updated!":
    print("Congratulations, all tests complete!\n")
else:
    print("Uh oh, there may be something wrong with the code\n")

#-----------------------Status-----------------------------#

print("Checking Status\n")


r = requests.post("http://challenge.code2040.org/api/status", data=token)
jsonresult = json.loads(r.text)

result = jsonresult["result"]

print("Your results... ")
for k,v in result.items():
    print(k,v)


