package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


////////////////////////////////////////////////////////////////////////////////
//
//Title:           p2 Balanced Search Tree
//Files:           BST.java, RBT.java, TestBST.java, TestRBT.java, CompareDS.java
//
//Course:          CS 400 Spring 2020 Lec 1
//Description:     This program creates and balances a binary search tree and red black tree
//
//Author:          Matt McNaught
//Email:           mmcnaught@wisc.edu
//Lecturer's Name: Debra Deppeler
//
///////////////////////////////////////////////////////////////////////////////
//
//Students who get help from sources other than their partner must fully 
//acknowledge and credit those sources of help here.  Instructors and TAs do 
//not need to be credited here, but tutors, friends, relatives, room mates, 
//strangers, and others do.  If you received no outside help from either type
//of source, then please explicitly indicate NONE.
//
//Persons:         Adam Schlondrop
//Online Sources:  geeksforgeeks.com
//
///////////////////////////////////////////////////////////////////////////////
// DO IMPLEMENT A BINARY SEARCH TREE IN THIS CLASS

/**
 * Defines the operations required of student's BST class.
 *
 * NOTE: There are many methods in this interface 
 * that are required solely to support gray-box testing 
 * of the internal tree structure.  They must be implemented
 * as described to pass all grading tests.
 * 
 * @author Deb Deppeler (deppeler@cs.wisc.edu)
 * @param <K> A Comparable type to be used as a key to an associated value.  
 * @param <V> A value associated with the given key.
 */
public class BST<K extends Comparable<K>, V> implements STADT<K,V> {
    
  /*
   * Class to store the nodes used in the BST
   */
  private class Node{
    
    //characteristics of the Node
    private K key;
    private V value;
    private Node left;
    private Node right;
    
    private Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
    

    private Node getLeft() {
      return left;
    }

    private void setLeft(Node left) {
      this.left = left;
    }

    private Node getRight() {
      return right;
    }

    private void setRight(Node right) {
      this.right = right;
    }

    private K getKey() {
      return key;
    }

    private V getValue() {
      return value;
    }
    
  }
  
  
  
  private Node root;
  private int numKeys;
  
    /**
     * Returns the key that is in the root node of this ST.
     * If root is null, returns null.
     * @return key found at root node, or null
     */
    public K getKeyAtRoot() {
        return root.getKey();
    }
    
    
    private BST<K, V>.Node getNodeWith(BST<K, V>.Node root, K key) throws IllegalNullKeyException, KeyNotFoundException{
      if(key == null)
        throw new IllegalNullKeyException();
      //if node is not found, return null
      if(root == null) {
        throw new KeyNotFoundException();
      }
      //if the node with the key is found, return the node
      if(root.getKey().equals(key))
        return root;
      //if the key is less than the current node key, search the left child
      if(key.compareTo(root.getKey()) < 0)
        return getNodeWith(root.getLeft(), key);
      //if the key is greater than the current node, search the right child
      if(key.compareTo(root.getKey()) > 0)
          return getNodeWith(root.getRight(), key);
      
      //if node is not found, return an error
      throw new KeyNotFoundException();
    }
    
    /**
     * Tries to find a node with a key that matches the specified key.
     * If a matching node is found, it returns the returns the key that is in the left child.
     * If the left child of the found node is null, returns null.
     * 
     * @param key A key to search for
     * @return The key that is in the left child of the found key
     * 
     * @throws IllegalNullKeyException if key argument is null
     * @throws KeyNotFoundException if key is not found in this BST
     */
    public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
      if(key == null)
        throw new IllegalNullKeyException();
      BST<K, V>.Node node = getNodeWith(root, key).getLeft();
      if(node == null)
         return null;
      return node.getKey();    
      }
    
    /**
     * Tries to find a node with a key that matches the specified key.
     * If a matching node is found, it returns the returns the key that is in the right child.
     * If the right child of the found node is null, returns null.
     * 
     * @param key A key to search for
     * @return The key that is in the right child of the found key
     * 
     * @throws IllegalNullKeyException if key is null
     * @throws KeyNotFoundException if key is not found in this BST
     */
    public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
      if(key == null)
        throw new IllegalNullKeyException();
      BST<K, V>.Node node = getNodeWith(root, key).getRight();
      if(node == null)
         return null;
      return node.getKey();
    }
    

    /**
     * Returns the height of this BST.
     * H is defined as the number of levels in the tree.
     * 
     * If root is null, return 0
     * If root is a leaf, return 1
     * Else return 1 + max( height(root.left), height(root.right) )
     * 
     * Examples:
     * A BST with no keys, has a height of zero (0).
     * A BST with one key, has a height of one (1).
     * A BST with two keys, has a height of two (2).
     * A BST with three keys, can be balanced with a height of two(2)
     *                        or it may be linear with a height of three (3)
     * ... and so on for tree with other heights
     * 
     * @return the number of levels that contain keys in this BINARY SEARCH TREE
     */
    public int getHeight() {
      //if the tree is empty, the height is 0
      if(numKeys == 0)
         return 0;
      //if the root is null the height is 0
      if(root == null)
        return 0;
      //if the root is a leaf, the height is 1
      if(numKeys == 1)
        return 1;
      //get the left and right subtree height
      int left = height(root.getLeft());
      int right = height(root.getRight());
      //return the higher height between the subtrees
      if(left >= right)
        return left;
      return right;
    }
    
    /*
     * Recursive helper method to calculate the height of the tree
     */
    private int height(BST<K, V>.Node root) {
      //if the end of a tree is reached, return back up the tree
      if(root == null)
        return 0;
      //get the left and right subtree height
      int right = height(root.getLeft());
      int left = height(root.getRight());
      //return the taller subtree
      if(right > left)
        return 1 + right;
      return 1 + left;
      
    }
    
    
    /**
     * Returns the keys of the data structure in sorted order.
     * In the case of binary search trees, the visit order is: L V R
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in-order
     */
    public List<K> getInOrderTraversal() {
      List<K> list = new ArrayList<K>();
      if(numKeys == 0)
        return list;
      //recursively add the nodes in order to the list
      return inOrderTraversalHelper(root, list);
    }
    
    /*
     * Recursively adds the nodes in the tree to the list
     * @return List of Keys in order
     */
    private List<K> inOrderTraversalHelper(BST<K, V>.Node root, List<K> list){
      if(root != null) {
        //add the left subtree then the root then the right tree
        inOrderTraversalHelper(root.getLeft(), list);
        list.add(root.getKey());
        inOrderTraversalHelper(root.getRight(), list);
      }
       return list;
    }
    
    /**
     * Returns the keys of the data structure in pre-order traversal order.
     * In the case of binary search trees, the order is: V L R
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in pre-order
     */
    public List<K> getPreOrderTraversal() {
      List<K> list = new ArrayList<K>();
      if(numKeys == 0)
        return list;
      //recursively add the nodes in preorder to the list
      return preOrderTraversalHelper(root, list);
    }
    
    /*
     * Recursively adds the nodes in the tree to the list
     * @return List of Keys in order
     */
    private List<K> preOrderTraversalHelper(BST<K, V>.Node root, List<K> list){
      if(root != null) {
        //add the root then the left tree then the right
        list.add(root.getKey());
        preOrderTraversalHelper(root.getLeft(), list);
        preOrderTraversalHelper(root.getRight(), list);
      }
       return list;
    }

    /**
     * Returns the keys of the data structure in post-order traversal order.
     * In the case of binary search trees, the order is: L R V 
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in post-order
     */
    public List<K> getPostOrderTraversal() {
      List<K> list = new ArrayList<K>();
      if(numKeys == 0)
        return list;
      //recursively add the nodes in preorder to the list
      return postOrderTraversalHelper(root, list);
    }
    
    /*
     * Recursively adds the nodes in the tree to the list
     * @return List of Keys in post-order
     */
    private List<K> postOrderTraversalHelper(BST<K, V>.Node root, List<K> list){
      if(root != null) {
        //add the left tree then the right then the root
        postOrderTraversalHelper(root.getLeft(), list);
        postOrderTraversalHelper(root.getRight(), list);
        list.add(root.getKey());
      }
       return list;
    }
    
    

    /**
     * Returns the keys of the data structure in level-order traversal order.
     * 
     * The root is first in the list, then the keys found in the next level down,
     * and so on. 
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in level-order
     */
    public List<K> getLevelOrderTraversal() {
      //create a queue to use for organizing the tree order
      Queue<BST<K, V>.Node> queue=new LinkedList<BST<K, V>.Node>();
      List<K> list = new ArrayList<K>();
      //add the root to add it first
      queue.add(root);
      //add every node to the list in level order
      while(!queue.isEmpty())
      {
          //create a tempNode to access each node and its children
          BST<K, V>.Node tempNode=queue.poll();
          //add the node and add its children to the queue to be added in the next level
          list.add(tempNode.getKey());
          if(tempNode.left!=null)
              queue.add(tempNode.left);
          if(tempNode.right!=null)
              queue.add(tempNode.right);
      }
        return list;
    }
    
    
    /** 
     * Add the key,value pair to the data structure and increase the number of keys.
     * If key is null, throw IllegalNullKeyException;
     * If key is already in data structure, throw DuplicateKeyException(); 
     * Do not increase the num of keys in the structure, if key,value pair is not added.
     */
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
      //if key is null, throw a null key exception
      if(key == null)
        throw new IllegalNullKeyException();
      
      //if the root is null, set the root to the new Node
      if(root == null) {
        root = new Node(key, value);
        numKeys++;
        return;
      }
      //if the key is a duplicate, throw a new duplicate exception
      if(contains(key))
        throw new DuplicateKeyException();
      
      insertHelper(root, key, value);
      numKeys++;
    }
    
    /*
     * helper for insert method to traverse down the tree to the right spot
     */
    private BST<K, V>.Node insertHelper(BST<K, V>.Node root, K key, V value){
      //if the current node is null, insert there
      if(root == null)
        return root;
      
      //if the key is less than the current node, insert left if no child otherwise keep searching left
      if(key.compareTo(root.getKey()) < 0) {
        if(root.left == null) {
          root.left = new Node(key,value);
          return root.left;
        }
        //search the left child
        return insertHelper(root.left, key, value); 
      }
      
      //if there is no right child return that slot
      if(root.right == null) {
        root.right = new Node(key,value);
        return root.right;
      }
      //if there is a child to the right, search down that way
      return insertHelper(root.right, key, value);
    }
    
    
    
    

    /** 
     * If key is found, remove the key,value pair from the data structure 
     * and decrease num keys, and return true.
     * If key is not found, do not decrease the number of keys in the data structure, return false.
     * If key is null, throw IllegalNullKeyException
     */
    public boolean remove(K key) throws IllegalNullKeyException {
      if(key == null)
        throw new IllegalNullKeyException();
      try {
        root = removeHelper(root, key);
        numKeys--;
      }catch(KeyNotFoundException e) {
        return false;
      }
        return true;
    }
    
    /*
     * helper for remove to recursively find and delete the node
     */
    private BST<K, V>.Node removeHelper(BST<K, V>.Node root, K key) throws KeyNotFoundException{
      if(root == null)
        throw new KeyNotFoundException();
      
      //if key is smaller than the node, go left
      if(key.compareTo(root.getKey()) < 0) {
        root.left = removeHelper(root.left, key);
      }
      //if key is larger than the node, go right
      else if(key.compareTo(root.getKey()) > 0) {
        root.right = removeHelper(root.right, key);
      }
      else {
        // this is the node to be deleted
        //if the node has one or no children return its child 
        if(root.getLeft() == null) {
          return root.right;
        }else if(root.getRight() == null) 
          return root.left;
      
        BST<K, V>.Node successor = inOrderSuccessor(root.right);
        root.key = successor.getKey();
        root.value = successor.getValue();
        
        root.right = removeHelper(root.right, root.getKey());

        
      } 
      return root;
    }
    
    /*
     * gets the in-order successor of the key
     */
    private BST<K, V>.Node inOrderSuccessor(BST<K, V>.Node root){
      BST<K, V>.Node successor = root;
      
      //iterate down the left subtree to the last node
      while(root.getLeft() != null) {
        successor = root.getLeft();
        root = root.getLeft();
      }
      return successor;
      
    }
    
    

    /**
     * Returns the value associated with the specified key.
     *
     * Does not remove key or decrease number of keys
     * If key is null, throw IllegalNullKeyException 
     * If key is not found, throw KeyNotFoundException().
     */
    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
      if(key == null)
        throw new IllegalNullKeyException();
        return getNodeWith(root, key).getValue();
    }

    
    
    /** 
     * Returns true if the key is in the data structure
     * If key is null, throw IllegalNullKeyException 
     * Returns false if key is not null and is not present 
     */
    public boolean contains(K key) throws IllegalNullKeyException { 
      try {
        if(key == null)
          throw new IllegalNullKeyException();
        getNodeWith(root, key);
        
      }catch(KeyNotFoundException e) {
        return false;
      }
        return true;
    }

    /**
     *  Returns the number of key,value pairs in the data structure
     */
    public int numKeys() {
        return numKeys;
    }
    
    
    /**
     * Print the tree. 
     *
     * For our testing purposes of your print method: 
     * all keys that we insert in the tree will have 
     * a string length of exactly 2 characters.
     * example: numbers 10-99, or strings aa - zz, or AA to ZZ
     *
     * This makes it easier for you to not worry about spacing issues.
     *
     * You can display a binary search in any of a variety of ways, 
     * but we must see a tree that we can identify left and right children 
     * of each node
     *
     * For example: 
     
           30
           /\
          /  \
         20  40
         /   /\
        /   /  \
       10  35  50 

       Look from bottom to top. Inorder traversal of above tree (10,20,30,35,40,50)
       
       Or, you can display a tree of this kind.

       |       |-------50
       |-------40
       |       |-------35
       30
       |-------20
       |       |-------10
       
       Or, you can come up with your own orientation pattern, like this.

       10                 
               20
                       30
       35                
               40
       50                  

       The connecting lines are not required if we can interpret your tree.

     */
    public void print() {
      printBinaryTree(root, 0);
    }
    
    /*
     * 
     * Recursively print out the binary tree to make a 2D visual diagram
     * 
     */
    public void printBinaryTree(BST<K,V>.Node root, int level){
      if(root==null)
           return;
      //print the right subtree and next level
      printBinaryTree(root.right, level+1);
      //for levels past the root, print the lines to show the links
      if(level!=0){
          for(int i=0;i<level-1;i++)
              System.out.print("|\t");
              System.out.println("|-------"+root.getKey());
      }
      else
        //print the root
          System.out.println(root.getKey());
      //print the left subtree in the next level
      printBinaryTree(root.left, level+1);
  }
    
} // copyrighted material, students do not have permission to post on public sites




//  deppeler@cs.wisc.edu
