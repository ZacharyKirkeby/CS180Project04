# CS180Project04

## Running Instructions



## Submission Log
***
Project Report: PDF File

Submitted by: William Hyun
***

Project File/Repo: Git copy of Current Repo

Submitted by: Zachary Kirkeby

***
## Class Descriptions
***
We utilize 6 total classes for the full execution of the program. Product.java represents a product object. Product
objects serve as the foundational object for the organization of transactions. Store.java represents a singular store,
much like product the overall program utilizes multiple different stores. Account is the foundational class for 
customers and seller objects, which are permission levels on user accounts. Customer and Seller both manage the
intrinsic properties and functions of a seller or customer, which will be detailed further below. Marketplace is the
central program for the program, managing functions from all other calls, handling user input, and calling the 
needed classes depending on input. 

### [Product.java](</src/Product.java>)
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
manual hard testing was done on a method by method basis to ensure each method behaved as predicted. JUnit cases are 
found [here](</src/ProductTestCases.java>)


### [Store.java](</src/Store.java>)
***
The store class handles the creation of the Store object. A Store object contains an arraylist of Products,
seller name, seller location, and the store name. Store.java has the standard getters/setters for a java class, allowing
for the modification of the name, location, sales and revenue. Store classes are all stored in a list within the
marketplace. Stores are also stored within sellers, as sellers hold their own list of stores. Stores directly manage
Product Objects. 

The store keeps track of purchases, can return product options, and hosts the sorting methods on a store
by store basis. The store constructor takes a name, location, and an associated seller. A store can be created with a 
list of products, or can be made with no starter products. Store has a toString method to print out all products 
contained within that specific store. Store facilitates sorting products within the store by price and by alphabetical
order. 

The store object also calculates store based statistics for the seller. It can access and display customer history, 
revenue from past purchases. The store can release a file that details purchases, revenue, and customer information.

For optional features, store manages starting a sale for a specific product as well as the implementation of a cap on 
the number of any one item that you can purchase. It also has the trigger method for starting a sale. The store is used
to stage these methods in order to correctly identify the product, which then is passed the appropriate arguments. 

The store was tested via JUnit test cases found [here](</src/StoreTestCases.java>). These were used to determine that 
the output from all methods behaved normally and was formatted correctly. On top of JUnit cases, Store was manually
tested as a part of our hard testing to ensure it behaved correctly, regardless of edge cases. This was the more robust 
testing that also included persistence testing by abruptly ending the program. 

### [Account.java](</src/Account.java>)
***
Account.java is the underlying class behind all accounts. Both users and sellers are based on common traits found within
Account. Note; Customer and Seller are not subclasses of Account. Account manages all functions for the creation of 
accounts, logging in, and deletion of accounts. Account is directly invoked from MarketPlace as a part of managing 
account settings. Account manages the account data database file, AccountData.txt which ensures persistence for all user
accounts. 

Account in of itself is not an object, it holds a list of all account credentials. Account has an arraylist holding 
emails, usernames, passwords, and roles. Creating an account takes a username, password, role, and email. The email is 
checked via regex to ensure it fits the general format of an email, assuming success all data is added to the lists and
are added to the account database. 

The login method checks that the entered username and password match records in the database. If they don't, a message 
is displayed. Deleting an account works a similar way, it takes the entered data, finds the matching database entry, and 
deletes it. Data stored in the arraylists are adjusted accordingly. 

Changing usernames are also handled through account, which updates the utilized data structures. Change password works
in a similar manner. 

Account has standard getters that take entered data for a user and will fetch the account role, password, username, and
email. There is a method to writing to the database file as well as a method for reading the file. All actions call
these two methods to ensure persistence. 

As a bonus feature, password encryption is also handled within Account. We do not store passwords as plaintext. The 
passwords are stored as a SHA 256 hash. While not the most advanced it is better than plaintext passwords. All password
operations involve checking the hashes against each other. 

As with all other classes, testing occurred in two main ways. The primary was through JUnit testing found
[here](</src/AccountTestCases.java>). In addition to testing each method through JUnit, as a part of maunal hard testing
methods were manually checked to ensure normal behavior, as anomalous behavior will break Customer and Seller objects
as well as disrupt MarketPlace functions. 

### [Customer.java](</src/Customer.java>)
***
The customer class is a user designated the customer permissions. At a high level, Customer directly interacts with 
Marketplace and Product. Customer handles the shopping cart feature, Customer Purchase history, the purchase history 
databases, and allows for the exporting of the customers purchase. 

ngl i have no idea whats going on here




### [Seller.java](</src/Seller.java>)
***
ngl i have no idea whats going on here

### [MarketPlace.java](</src/MarketPlace.java>)
***
Overarching handler for Sellers, Store, and Customer classes. Creates a looping menu with options depending on user
designation. First prompts the user to login or register a new account. From there, a looping menu is created showing 
options based on user designation. The marketplace uses a scanner for all system input and output. The marketplace will
keep running until the program is terminated. Users can exit at any point. 

#### Seller Options
***
If the user is designated as a seller, options are presented to create a new store, manage an existing store, view
statistics, delete an existing store, modify their account, or logout. Modify account and logout are not unique to 
seller designated accounts, these will be detailed further below. All of these directly work with the Seller class. 

Creating a new store calls the Seller class, see Seller section. Most store actions are handled through seller. 
Within editing/modifying a store, the options to update products, change prices, and change quantities are presented. 
The Seller can also create or delete products, ie add/remove it from their store. File support is included, products can
be added via CSV file. The Seller can also start a sale for any product in their store, which works with the Store and
Product classes. The same process is used to implement a product sale cap. 

The statistics menu presents sorting options, customers and purchase records, shopping cart records, and the ability to 
export product listings to a file. This is where the seller can see their total sales by store, as well as total revenue
by store. 

All of the above is handled through a series of nested switch statements. All menus are presented as a list with a
number corresponding to a different sub menu or choice. There are multiple sub menus. 

#### Customer Options
***
The customer designated users are prompted with search options. They can search by store specifically, by product, by 
description, or just return everything. The customer can also view all products in sorted form. The sorting options
include by cheapest product, most expensive, and availability. 

The customer has the option to leave a review on a product. In order to account for repetition among product names, the
customer has to specify which store the product comes from. The customer can view existing reviews on products as well. 

Within the shopping cart menu, the user can add products to the shopping cart. These will persist between sessions as
they are stored in a file. A customer can change the quantity of any product in the cart, as well as remove a product 
from the cart. A customer can return to shopping as well, or purchase all products in the shopping cart. 


#### Account Management (General Feature)
***
Users, regardless of designation can change their username, role, password and can also logout. All of these features 
interact with primarily the Account class, as well as the customer or seller class depending on the user permission
role. Logging out prevents any function of the application from being used until one logs back in. Changing any account
credentials will be saved and updated in the appropriate database files. 

As for testing. There exists a set of JUnit test cases for marketplace, found [here](</src/MarketPlaceTestCases.java>).
These test to ensure the menus behave correctly, primarily by utilizing different functions and method calls in order to 
ensure there is a consistent flow within Marketplace logic. Manual testing primarily has involved running Marketplace
multiple times, checking each menu and sub menu to ensure that MarketPlace and it's calls all work appropriately. 

***

## Project Selection Requirements

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
