import { useEffect, useState } from "react";
import React from 'react';
import { Payment } from "../models/Payment.ts";
import axios from "axios";

export const Payments = () => {
    const [payments, setPayments] = useState<Payment[]>([]);

    useEffect(() => {
        const getAllPayments = async () => {
            try {
                const response = await axios.get("http://localhost:8080/payment");
                setPayments(response.data.map((payment: any) => 
                    new Payment(
                        payment.id, 
                        payment.amount, 
                        new Date(payment.date), 
                        payment.taxReturn, 
                        payment.method
                    )
                ));
            } catch (error) {
                console.error("Error fetching payments:", error);
            }
        };

        getAllPayments();
    }, []);

    return (
        <main>
            <h1>Payments</h1>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Amount</th>
                        <th>Date</th>
                        <th>Tax Return ID</th>
                        <th>Method</th>
                    </tr>
                </thead>
                <tbody>
                    {payments.map(payment => (
                        <tr key={payment.id}>
                            <td>{payment.id}</td>
                            <td>${payment.amount.toFixed(2)}</td>
                            <td>{payment.date.toLocaleDateString()}</td>
                            <td>{payment.taxReturn ? payment.taxReturn.id : "N/A"}</td>
                            <td>{payment.method}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </main>
    );
};
