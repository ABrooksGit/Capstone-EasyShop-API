Easy-Shop-API

This easy shop api will allow a user from the front end to be able to create a website that can take advantage this code. During this capstone we were handed a website and code which was filled with bugs and incomplete code. So I looked through the pieces of code that needed to be done and filled out everything possible, from what was given.

Two bugs that needed to be fixed were sorting the products by the price. Originally the price only accepted a single value and want an inbetween. In order to fix this bug, I had to go into the SqlDao for products and add another price point to search between. Then apply it to the prepared statement.

The next bug was an incorrect update method for products. At the start, instead of calling the update method from the SQLDAO, it was calling the create method. So the simple act of changing the name fixes that bug.

What was tasked next, is creating the controller and SQLDAO for categories. Since barely any of the code was written for this, I mainly had to write out a lot of it from scratch. while doing so, there was a lot of access to code alreadly avaliable to know how things are written and done, to be able to infer how to progress.

Next up there were some optional tasks that needed to be completed, but wasn't top priority. The first one up is being able to add a shopping cart and the entire functionality that comes with it. Like being able to add an item, the quantity of that item. This was a a lot harder to get through because of the amount of thought needed to go through it, but the logic of it isn't hard to write, but due to the instructions there are details that were left out which would help, this is mostly due to how the website opertated.

The next optional task is being able to edit a users profile. This would include the users: first name, last name, phone number, email address, city, state, and zip code. Then being able to update it when needed. So when a user is created, they will gain a profile which will come out as blank. Once this is done, the user can input in their information and update it as many times as needed.

The last optional task is going back to the Shopping Cart and adding a checkout function, which not only checks out the items in the cart, but also passes through the profile information. For this to work there was a number of things that was needed. Since this optional didnt have any of the classes already there, I had to create it from scratch. The thought process to creating this was a lot harder and harder to grasp because of how its presented. But after doing so the shopping cart can be checked out and all the items attached to that check out will be seen too.

Lastly Postman was used to verify and check if these worked without needing a frontend. Even though a website was provided, there are still some features not added to the frontend and can only be checked through postman. There were also tests included in a form of a collection that can run the code and check if everything is working. Which I used a lot to test.


![Bug 1, Before and After(PreparedStatement)](https://github.com/user-attachments/assets/47526b07-40a1-4627-a5d2-358a742b407a)
![Bug 1, Before and After(SQL)](https://github.com/user-attachments/assets/1bad0ac6-6e40-4496-bfe2-3ed340698cf7)
![Bug 2 and the FIx](https://github.com/user-attachments/assets/f1642a9f-68cd-4d3f-a1fb-e89ccf76ba7d)
![postman optional easyshop collection](https://github.com/user-attachments/assets/789c7221-7595-46c8-baa4-d070ef43452a)
![postman easyshop collection](https://github.com/user-attachments/assets/f7b4ab7e-05c8-4e42-a08d-7b3dd527dc16)
