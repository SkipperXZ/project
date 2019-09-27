import base64
import requests

url1 = "http://localhost:8080/api/postAPDetail"
url2 = "http://localhost:8080/api/upload"

headers = {'Content-type': 'application/json'}

with open('test_image_1.jpg', mode='rb') as file:
    img = file.read()
img = base64.encodebytes(img).decode("utf-8")


json_data = {
                "uuid" : "image_test_1",
                "userID" : "user2",
                "latitude" : 123.324,
                "longitude" : 2525.55,
                "timeStamp" : "2014-01-01T00:00:00"
               
            }  
image_data = {
             "uuid" : "image_test_1",
             "imageData" : img
}  

r1 = requests.post(url1, json=json_data,headers=headers)
r2 = requests.post(url2, json=image_data,headers=headers)
print(r1.text)
print(r2.status_code)