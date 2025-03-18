import axios from 'axios';
import { useState, useEffect } from 'react';
import { Client } from '../models/Client.ts';
import React from 'react';

export const Clients = () => {

    const [clients, setClients] = useState<Client[]>([]);

    const getAllClients = async () => {
        await axios.get("http://localhost:8080/client")
            .then(response => {
                setClients(response.data.map((client: any) => 
                    new Client(
                        client.id, client.firstName, client.lastName,
                        client.ssn, client.hashed_ssn, client.dob,
                        client.phone, client.email, client.address1,
                        client.address2, client.city, client.state, client.zip
                    )
                ));
            })
            .catch(error => {
                console.error("Error fetching clients:", error);
            });
    };

    useEffect(() => {
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
                    {
                        clients.map(client => (
                            <tr key={client.id}>
                                <td>{client.id}</td>
                                <td>{client.firstName} {client.lastName}</td>
                                <td>{client.email}</td>
                                <td>{client.phone}</td>
                                <td>{client.city}</td>
                                <td>{client.state}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </main>
    );
};