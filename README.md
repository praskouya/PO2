# Object-Oriented Programming 2 Projects

This repository contains projects and assignments completed as part of the Object-Oriented Programming 2 course at Łódź University of Technology. The course aimed to deepen knowledge in object-oriented programming and apply advanced concepts in various projects.

## Contents

The repository includes the following projects:

### Project 1: Number Guessing Game

A program that generates a random number within a range of 0 to N, where N is provided via command line arguments. The user is asked to guess the number, and if the guess is incorrect, they are informed whether the number is higher or lower. If the guess is correct, the program reports the number of attempts and asks if the user wants to play again. The program handles various user input errors related to command line arguments.

### Project 2: Shopping List

A text-based shopping list program that reads a list of purchasable items from a text file, categorized by type. The program allows users to:
- Add items to the shopping list by selecting categories and products.
- Display all items or items from a specific category.
- Remove items or clear the entire shopping list.
- Save and load the shopping list to and from disk, ensuring only one list is saved at a time.

### Project 3: Phone Book

A class `PhoneNumber` with two fields: area code and phone number, implementing the `Comparable` interface. An abstract class `Entry` with subclasses `Person` and `Company` is used to represent different types of entries in a phone book. The `Entry` class has an abstract method `describe()` to describe the entry. The `Person` class contains information about name, surname, address, and phone number, while the `Company` class includes name, address, and phone number. Multiple `Person` and `Company` objects are stored in a `TreeMap` using the phone number as the key. The phone book is displayed using an iterator, and entries with identical street names are removed, with the updated map displayed.


### Project 4: Vectors

A program that prompts the user to input two vectors (sequences of numbers), with vector termination indicated by pressing Enter. Non-numeric input is ignored. The program attempts to add the vectors if they are of equal length; otherwise, a custom exception `VectorsDifferentLengthException` is thrown to report the lengths of the vectors. If the vectors are of equal length, the result is saved to a file. If not, the user is asked to re-enter the vectors.

### Project 5: Notification Server

A network program consisting of a client application and a server application for queuing and sending notifications.

The client application 
- Connects to the server with exception handling.
- Collects wanted waiting time and content of the notification from the user.
- Displays received notifications.

The server application 
- Queues notifications received from the client application.
- Sends notifications to clients at the specified time.
- Is capable of handling multiple clients simultaneously.

### Project 6: Checkers

A checkers game program with GUI, which allows to play a game between 2 players using the same computer. The programme validates the correctness of the players' moves.

