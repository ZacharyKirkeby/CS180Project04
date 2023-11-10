# Part 1

## Project Overview
***
Our group selected Option 3 for our specific implementation of Project 4. Since our group has 5 members, we included all
three selection options. 

From a high level, our project is composed of 6 classes, which implement all core requirements as well as selective
requirements. 

### Account
***


### Customer
***

### MarketPlace
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
Users, regardless of designation can change their username, email, password and can also logout. 

### Product

### Seller

### Store

The store class handles the creation of the Store object. A Store object contains an arraylist of Products,
seller name, seller location, and the store name. 

The store keeps track of purchases, can return product options, and hosts the sorting methods on a store
by store basis. 

