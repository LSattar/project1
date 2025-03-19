import axios from 'axios';
import { useState, useEffect } from 'react';
import { Client } from '../models/Client.ts';
import React from 'react';
import { ClientProfile } from '../components/ClientProfile.tsx';

export const Clients = () => {
    const [clients, setClients] = useState<Client[]>([]);
    const [selectedClientId, setSelectedClientId] = useState<number | null>(null);

    useEffect(() => {
        const getAllClients = async () => {
            try {
                const response = await axios.get("http://localhost:8080/client");
                setClients(response.data.map((client: any) => 
                    new Client(
                        client.id, client.firstName, client.lastName,
                        client.ssn, client.hashed_ssn, client.dob,
                        client.phone, client.email, client.address1,
                        client.address2, client.city, client.state, client.zip, client.employmentSector
                    )
                ));
            } catch (error) {
                console.error("Error fetching clients:", error);
            }
        };

        getAllClients();
    }, []);

    return (
        <main>
            <h1>Clients</h1>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>City</th>
                        <th>State</th>
                    </tr>
                </thead>
                <tbody>
                    {clients.map(client => (
                        <tr key={client.id}>
                            <td>{client.id}</td>
                            <td>
                                <button 
                                    onClick={() => setSelectedClientId(client.id)}
                                    style={{ 
                                        background: "none", 
                                        border: "none", 
                                        color: "blue", 
                                        textDecoration: "underline", 
                                        cursor: "pointer" 
                                    }}
                                >
                                    {client.firstName} {client.lastName}
                                </button>
                            </td>
                            <td>{client.email}</td>
                            <td>{client.phone}</td>
                            <td>{client.city}</td>
                            <td>{client.state}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {selectedClientId && (
                <div className="main-content">
                <div className="client-profile-overlay">
                    <ClientProfile 
                        clientId={selectedClientId} 
                        onClose={() => setSelectedClientId(null)} 
                    />
                </div>
                </div>
            )}
        </main>
    );
};