import axios from 'axios';
import { useState, useEffect } from 'react';
import { Client } from '../models/Client.ts';
import React from 'react';
import { ClientProfile } from '../components/ClientProfile.tsx';
import { TaxReturn } from '../models/TaxReturn.ts';

export const TaxReturns = () => {
const [taxReturns, setTaxReturns] = useState<TaxReturn[]>([]);
    const [selectedTaxReturnId, setSelectedTaxReturnId] = useState<number | null>(null);

    useEffect(() => {
        const getAllTaxReturns = async () => {
            try {
                const response = await axios.get("http://localhost:8080/tax-return");
                setTaxReturns(response.data.map((taxReturn: any) => 
                    new TaxReturn(
                        taxReturn.id, taxReturn.client, taxReturn.cpa, taxReturn.year, taxReturn.status,
                         taxReturn.amountPaid, taxReturn.amountOwed, taxReturn.cost, taxReturn.creationDate, 
                         taxReturn. updateDate, taxReturn.employmentSector, taxReturn.totalIncome, taxReturn.adjustments, 
                         taxReturn.filingStatus
                    )
                ));
            } catch (error) {
                console.error("Error fetching tax returns:", error);
            }
        };

        getAllTaxReturns();
    }, []);

    return (
        <main>
            <h1>Tax Returns</h1>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Client Name</th>
                        <th>Year</th>
                        <th>Amount Owed</th>
                        <th>Amount Paid</th>
                        <th>Employment Sector</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    {taxReturns.map(taxReturn => (
                        <tr key={taxReturn.id}>
                            <td>{taxReturn.id}</td>
                            <td>
                                <button 
                                    onClick={() => setSelectedTaxReturnId(taxReturn.id)}
                                    style={{ 
                                        background: "none", 
                                        border: "none", 
                                        color: "blue", 
                                        textDecoration: "underline", 
                                        cursor: "pointer" 
                                    }}
                                >
                                    {taxReturn.client.firstName} {taxReturn.client.lastName}
                                </button>
                            </td>
                            <td>{taxReturn.year}</td>
                            <td>{taxReturn.amountOwed}</td>
                            <td>{taxReturn.amountPaid}</td>
                            <td>{taxReturn.employmentSector.employmentSectorName}</td>
                            <td>{taxReturn.status}</td>
                        </tr>
                    ))}
                </tbody>
            </table>


        </main>
    );
};