package com.example.donation50.models;

public class Donation
{
    public String _id;
    public int    amount;
    public String paymenttype;
    public int    upvotes;
    public int __v;

    public Donation (int amount, String method, int __v, int upvotes)
    {
        this.amount = amount;
        this.paymenttype = method;
        this.__v = __v;
        this.upvotes = upvotes;
    }

    public Donation ()
    {
        this.amount = 0;
        this.paymenttype = "";
        this.__v = 0;
        this.upvotes = 0;
    }

    public String toString()
    {
        return _id + ", " + amount + ", " + paymenttype + ", " + __v +" , " + upvotes;
    }
}