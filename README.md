# CS180Project04

## Running Instructions

## Submission Log
***

Project Report: 
Submitted by: 

***

Project File/Repo:
Submitted by:

***
## Class Descriptions

We utilize 6 total classes for the full execution of the program. Product.java represents a product object. Product
objects serve as the foundational object for the organization of transactions. Store.java represents a singular store,
much like product the overall program utilizes multiple different stores. Account is the foundational class for 
customers and seller objects, which are permission levels on user accounts. Customer and Seller both manage the
intrinsic properties and functions of a seller or customer, which will be detailed further below. Marketplace is the
central program for the program, managing functions from all other calls, handling user input, and calling the 
needed classes depending on input. 

### Product.java
***
Product.java is the file representing a generic product object. A product is sold in a store by a seller, can be put in
a shopping cart, and can be purchased by a customer. Products are stored in a database file for persistance purposes,
are stored in a list within each store, and are on some occasions directly invoked for sorting purposes. Generally, 
all products are invoked through store, which is usually invoked from Seller or Marketplace.

A standard product has a name, description, integer value for the current
stock, an integer for the total number sold, and a double for the current price. As a bonus feature added, products also 
have a boolean value for being on sale, a sale price, a number of that item on sale, and the amount of that product sold
while on sale. There are multiple constructors for Products, allowing for the inclusion or exclusion of a description.
Product possesses a range of standard getters and setters for price, name, description, quantity sold, and stock. 

In addition to more basic methods, Products have a method for direct purchasing. Purchasing first checks if the product
is on sale. If the product is on sale, and the purchase does not exceed any purchase limits, be it sale limits,
general limits, or quantity constraints, the sale quantity is decremented and overall quantity is decrement. If the
product is not on sale, the same checks for a valid purchase are made, and quantity is decremented. If a product is out
of stock, an error message will be displayed. 

Product also houses a method to sort products by price. 

Product houses revenue calculations, allowing total revenue from that particular product to be calculated. Methods to
implement a sale limit, start a sale, and assign traits to the sale also exist. Getters and setters for those methods
also exist. 

As for testing, JUnit test cases were written to test the different functions of Product. In addition to JUnit cases, 
manual hard testing was done on a method by method basis to ensure each method behaved as predicted. 


### Store.java
***
The store class handles the creation of the Store object. A Store object contains an arraylist of Products,
seller name, seller location, and the store name. Store.java has the standard getters/setters for a java class, allowing for the modification of the name, location, sales and revenue. 

The store keeps track of purchases, can return product options, and hosts the sorting methods on a store
by store basis. Store has a toString method to print out all products contained within that specific store. Store facilitates sorting products within the store by price and by alphabetical order. 

The store object also calculates store based statistics for the seller. It can access and display customer history, revenue from past purchases. 

For optional features, store manages starting a sale for a specific product as well as the implementation of a cap on the number of any one item that you can purchase. 

### Account.java

### Customer.java

### Seller.java

### MarketPlace.java
***
Overarching handler for Sellers, Store, and Customer classes. Creates a looping menu with options depending on user
designation. First prompts the user to login or register a new account. From there, a looping menu is created showing 
options based on user designation. 

#### Seller Options
***
If the user is designated as a seller, options are presented to create a new store, manage an existing store, view
statistics, delete an existing store, modify their account, or logout. Modify account and logout are not unique to 
seller designated accounts. 

Creating a new store calls the Seller class, see Seller section. Most store actions are handled through seller. 
Within editing/modifying a store, the options to update products, change prices, and change quantities are presented. 
The Seller can also create or delete products, ie add/remove it from their store. File support is included, products can
be added via CSV file. 

The statistics menu presents sorting options, customers and purchase records, shopping cart records, and the ability to 
export product listings to a file. 

#### Customer Options
***
The customer designated users are prompted with search options. They can search by store specifically, by product, or
just return everything. 

Lastly, the customer can purchase items, add to a shopping cart, view their shopping cart ... 

#### Account Management (General Feature)
***
Users, regardless of designation can change their username, role, password and can also logout. 


















## TODO List

Add Documentation to each class
Write comments/explain what stuff does

Primary authors of Files -> add writeup of file to Writeups folder

Everyone -> work on individual write up files

Document Testing methods

Come up with Setup Instructions

## Selected Option Requirements

### Option 3
The third option is to implement the official marketplace of the application. The marketplace will allow sellers to list their products and customers to purchase them. 

Looking for an example? Review the listing pages for any online store. 


Reminder: You can assume that only one user is accessing the application at a time. A seller might log in, list a product, then log out. A customer could then log in and purchase the item. 

Your implementation must have the following: 
#### Core
##### Market
The marketplace will be a centralized page listing available products for purchase. 
Products will include the following information: 
Name of the product
The store selling the product. 
A description of the product
The quantity available for purchase
##### The price
The marketplace listing page will show the store, product name, and price of the available goods. Customers can select a specific product to be taken to that product's page, which will include a description and the quantity available. 
When items are purchased, the quantity available for all users decreases by the amount being purchased. 
##### Sellers
Sellers can create, edit, or delete products associated with their stores. 
Sellers can view a list of their sales by store, including customer information and revenues from the sale. 
##### Customers
Customers can view the overall marketplace listing products for sale, search for specific products using terms that match the name, store, or description, and sort the marketplace on price or quantity available. 
Customers can purchase items from the product page and review a history of their previously purchased items. 
#### Selections
##### Files
All file imports must occur as a prompt to enter the file path.  
Sellers can import or export products for their stores using a csv file. 
All product details should be included, with one row per product. 
Customers can export a file with their purchase history.  
##### Statistics
Sellers can view a dashboard that lists statistics for each of their stores.
Data will include a list of customers with the number of items that they have purchased and a list of products with the number of sales. 
Sellers can choose to sort the dashboard.
Customers can view a dashboard with store and seller information.
Data will include a list of stores by number of products sold and a list of stores by the products purchased by that particular customer. 
Customers can choose to sort the dashboard.
##### Shopping cart
Customers can add products from different stores to a shopping cart to purchase all at once, and can remove any product if they choose to do so. The shopping cart is preserved between sessions, so a customer may choose to sign out and return to make the purchase later.  
Sellers can view the number of products currently in customer shopping carts, along with the store and details associated with the products. 
#### Optional Features: 
Sellers can elect to hold sales that reduce the price of a product until a specified number of units are sold. Customers will be informed of the original and sale price when browsing the marketplace. 
Customers can leave reviews associated with specific products from sellers. Other customers can view the reviews after they post. Sellers may view reviews on their products. 
Sellers may set a per product order quantity limit that prohibits customers from ordering more units than the limit. Customers will not be able to place any additional orders for a product after they reach the limit, unless the seller increases or removes it. 
