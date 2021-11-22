# Shopping-Spending tracker

## Shop smart, Spend wise!

### Designed by Xiaoyang Zhang 


This project aims to create a shopping-budget-tracker application for customer to use 
before, during, and after the shopping.

### What does the shopping tracker do
- **Before the shopping**
  - Check out what you have bought with visualized dates (avoid wasting)
  - Categorize your items based on it's property 
  - Add whatever you plan to buy to the shopping list
  - Set the budget for this shopping trip
  
- **During the shopping**
  - Cross the things off that have already added to the shopping cart
  
- **After the shopping**
  - Enter the expenses of the objects you bought and keep track in the "Spending" page
  - Visualize all your income, expenses, and balance

### Who will use it

-*For everyone who ...*
- want to be well-prepared before going into the supermarket
- is interested in keeping track the objects they have
- would like to keep track of the balance, manage and spend money wisely
- love life, pursue a healthy balance between goods and money

### My inspiration for this project

As an international student who lives alone on campus, I need to go shopping weekly to prepare for the food, necessities,
and anything I needed for the following week. Without a list, I always feel disoriented and overwhelmed by the dazzling 
shelves in the shopping market. Not only do I wasted a lot of time figuring out what I really need, but also wasted my 
money to buy many kinds of stuffs that I actually don't need. Sometimes I even repurchased or over purchase something 
that cause things to expire in my fridge later. Since my living expenses are limited, I realized the importance to have
an organized shopping list and learn to manage my money by keeping track of my balance. As many of my friends also have
this problem, I can't imagine how helpful it would be for me and hopefully for many other people to have a tool that 
combines these two applications. Therefore, I want to create an intelligent tool that could bring efficiency and 
happiness to life, recognize and appreciate what we own, have a more pleasant shopping experience, and better 
managing our relationship with money.


## User Stories
### For the Home page:
- As a user, I want to be able to add an item to my home page
- As a user, I want to be able to mark an item as my favorite at home
- As a user, I want to be able to delete an item from the Home
- As a user, I want to be able to view the total items I have at home page
- As a user, I want to be able to view what items and the amount I have in certain categories and favorite list
- As a user, I want to be able to store the bought items to home

### For the shopping list:
- As a user, I want to be able to set the budget for this shopping
- As a user, I want to be able to add an item based on category to my shopping list
- As a user, I want to be able to delete an item from my shopping list
- As a user, I want to be able to view what I needed to buy and have already bought
- As a user, I want to be able to mark an item as already added to the cart (bought)

### For the Spending & Transaction:
- As a user, I want to be able to view the list of transactions and the total number of transactions
- As a user, I want to be able to view my income and record new income.
- As a user, I want to be able to view total expenses and balance on spending page
- As a user, I want to be able to enter the price of bought item in order
- As a user, I want to be able to enter the price for specific bought item

### Data persistence: 
- As a user, I want to be given the option to save and 
load my previous home's, spending's, and shopping-list's data from file.

### Phase 4: Task 2 - EventLog
- Budget set to: 100.0 dollars. 
- Income set to: 1000.0 dollars. 
- Added 1 bread (Food) to the to-buy list. 
- Added 3 apple (FruitAndVegetables) to the to-buy list. 
- Added 1 milk (Drinks) to the to-buy list. 
- Added 1 toilet paper (Necessities) to the to-buy list. 
- Added 1 lipstick (Others) to the to-buy list. 
- Added 1 water (Drinks) to the to-buy list. 
- Deleted water from the to-buy list. 
- Bought 3 apple at 2021-11-20, added to transaction list.
- Deleted apple from the to-buy list. 
- Bought 1 milk at 2021-11-20, added to transaction list. 
- Deleted milk from the to-buy list. 
- Bought 1 bread at 2021-11-20, added to transaction list. 
- Deleted bread from the to-buy list. 
- apple's price is set to 10.0 dollars. 
- bread's price is set to 5.99 dollars. 
- milk's price is set to 6.99 dollars.


### Phase 4: Task 3 - Reflection
If I had more time to work on the project, I would like to improve my code in following ways:
- First, I would like to improve my ShoppingList class's cohesion and decrease the coupling between the ShoppingList class 
and Transaction class. As for now, the ShoppingList class is also doing some jobs that would be better to be done in the 
transaction class, such as adding the bought item's to transaction list. I should make the responsibility of ShoppingList 
and Transaction class be more clear.
- Second, ShoppingList class has a bought list (list of items) and also a transaction list (list of transactions) 
and they all keep track of the bought items, which is quite redundant. I could just let the transaction list to take the 
responsibility instead of duplicated adding and removing. 
- Third, I noticed that the transaction history serves the same functionality as home, so I would like to combine home 
and transaction list into one class (shown in one tab in gui) to keep track of all the things bought categorized based on 
categories.