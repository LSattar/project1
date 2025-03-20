import axios from 'axios';
import { useState, useEffect } from 'react';
import { Client } from '../models/Client.ts';
import React from 'react';
import { ClientProfile } from '../components/ClientProfile.tsx';
import { TaxReturn } from '../models/TaxReturn.ts';
import { Cpa } from '../models/Cpa.ts';
import { TaxReturnProfile } from '../components/TaxReturnProfile.tsx';
import { EditTaxReturn } from '../components/EditTaxReturn.tsx';
import { NewTaxReturn } from '../components/NewTaxReturn.tsx';

export const TaxReturns = () => {
    const [taxReturns, setTaxReturns] = useState<TaxReturn[]>([]);
    const [selectedTaxReturnId, setSelectedTaxReturnId] = useState<number | null>(null);
    const [editingTaxReturnId, setEditingTaxReturnId] = useState<number | null>(null);
    const [addingTaxReturn, setAddingTaxReturn] = useState<boolean> (false);

    const getAllTaxReturns = async () => {
        try {
            const response = await axios.get("http://localhost:8080/tax-return");
            setTaxReturns(response.data.map((taxReturn: any) =>
                new TaxReturn(
                    taxReturn.id, taxReturn.client, taxReturn.cpa, taxReturn.year, taxReturn.status,
                    taxReturn.amountPaid, taxReturn.amountOwed, taxReturn.cost, taxReturn.creationDate,
                    taxReturn.updateDate, taxReturn.employmentSector, taxReturn.totalIncome, taxReturn.adjustments,
                    taxReturn.filingStatus
                )
            ));
        } catch (error) {
            console.error("Error fetching tax returns:", error);
        }
    };

    useEffect(() => {
        getAllTaxReturns();
    }, []);

    return (
        <main>
            <h1>Tax Returns</h1>
            <button onClick={() => setAddingTaxReturn(true)}>Create New Tax Return</button>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Client Name</th>
                        <th>Year</th>
                        <th>Amount Owed</th>
                        <th>Amount Paid</th>
                        <th>Employment Sector</th>
                        <th>Assigned To</th>
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
                                >
                                    {taxReturn.client.firstName} {taxReturn.client.lastName}
                                </button>
                            </td>
                            <td>{taxReturn.year}</td>
                            <td>${taxReturn.amountOwed}</td>
                            <td>${taxReturn.amountPaid}</td>
                            <td>{taxReturn.employmentSector.employmentSectorName}</td>
                            <td>{taxReturn.cpa.firstName} {taxReturn.cpa.lastName}</td>
                            <td>{taxReturn.status}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {selectedTaxReturnId && !editingTaxReturnId && !addingTaxReturn && (
    <div className="main-content">
        <div className="client-profile-overlay">
            <div className="client-profile">
                <TaxReturnProfile
                    taxReturnId={selectedTaxReturnId}
                    onClose={() => setSelectedTaxReturnId(null)}
                    setEditingTaxReturnId={setEditingTaxReturnId}
                />
            </div>
        </div>
    </div>
)}

{editingTaxReturnId && !addingTaxReturn && (
    <div className="main-content">
        <div className="client-profile-overlay">
            <div className="client-profile">                         
                <EditTaxReturn
                    taxReturn={taxReturns.find(t => t.id === editingTaxReturnId)!}
                    updateTaxReturn={getAllTaxReturns}
                    onCancel={() => setEditingTaxReturnId(null)}
                />
            </div>
        </div>
    </div>
)}

{addingTaxReturn && (
    <div className="main-content">
        <div className="client-profile-overlay">
            <div className="client-profile">                         
                <NewTaxReturn
                    addTaxReturnToList={getAllTaxReturns}
                    onCancel={() => setAddingTaxReturn(false)}
                />
            </div>
        </div>
    </div>
)}
        </main>
    );
};