import { useState, useEffect } from "react";
import axios from "axios";
import { Client } from "../models/Client.ts";
import React from 'react';
import '../css/clientprofile.css';
import { EditClient } from "./EditClient.tsx";

interface ClientProfileProps {
    clientId: number;
    onClose: () => void;
}

export const ClientProfile = ({ clientId, onClose }: ClientProfileProps) => {
    const [client, setClient] = useState<Client | null>(null);
    const [isEditing, setIsEditing] = useState(false);

    console.log("Profile opened");

    useEffect(() => {
        if (!clientId) return;

        const fetchClient = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/client/${clientId}`);
                const clientData = response.data;
                setClient(new Client(
                    clientData.id, clientData.firstName, clientData.lastName,
                    clientData.ssn, clientData.hashed_ssn, clientData.dob,
                    clientData.phone, clientData.email, clientData.address1,
                    clientData.address2, clientData.city, clientData.state, clientData.zip, clientData.employmentSector
                ));
            } catch (error) {
                console.error("Error fetching client details:", error);
            }
        };

        fetchClient();
    }, [clientId]);

    const updateClient = async (updatedClient: Client) => {
        try {
            await axios.put(`http://localhost:8080/client/${updatedClient.id}`, updatedClient);
            setClient(updatedClient);
            setIsEditing(false); 
        } catch (error) {
            console.error("Error updating client:", error);
        }
    };

    const deactivateClient = async (deactivatedClient : Client) => {
        try{
            await axios.put(`http://localhost:8080/client/${deactivatedClient.id}/deactivate`)
            setIsEditing(false);
        } catch (error){
            console.error("Error deactivating client:", error);
        }
    }

    if (!client) return null;

    return (
            <div className="client-profile">
            {isEditing ? (
                <EditClient client={client} updateClient={updateClient} onCancel={() => setIsEditing(false)} />
            ) : (
                <>
                    <h2>Client Profile</h2>
                    <h3>Basic Info</h3>
                    <p><strong>ID: </strong> {client.id}</p>
                    <p><strong>Name: </strong> {client.firstName} {client.lastName}</p>
                    <p><strong>DOB: </strong> {(!client.dob) ? null : client.dob.toLocaleString()}</p>
                    <p><strong>SSN: </strong> 
                        <span className="ssn-container">
                            <span className="hidden">{client.ssn}</span>
                            <span className="hover"> Show SSN</span>
                        </span>
                    </p>
                    <p><strong>Employment Sector: </strong> {client.employmentSector ? client.employmentSector.employmentSectorName : "Not Available"}</p>

                    <h3>Contact Info</h3>
                    <p><strong>Phone: </strong> {client.phone}</p>
                    <p><strong>Email: </strong> {client.email}</p>
                    <p><strong>Address 1: </strong> {client.address1}</p>
                    <p><strong>Address 2: </strong> {client.address2}</p>
                    <p><strong>City: </strong> {client.city}</p>
                    <p><strong>State: </strong> {client.state}</p>
                    <p><strong>Zip: </strong> {client.zip}</p>

                    <div className="profile-buttons">
                        <button className="button-update" onClick={() => setIsEditing(true)}>Edit <img className = "button-image" src = "/images/pencil-white.png"></img></button>
                        <button className="button-close" onClick={onClose}>Close </button>
                        <button onClick={() => deactivateClient(client)}>Deactivate</button>
                    </div>
                </>
            )}
        </div>
    );
};