import { useState, useEffect } from "react";
import axios from "axios";
import { Client } from "../models/Client.ts";
import React from 'react';
import '../css/clientprofile.css'

interface EmploymentSectorProps {
    employmentSectorId: number | null;
    onClose: () => void;
}

export const EditEmploymentSector = ({ employmentSectorId, onClose }: EmploymentSectorProps) => {
    const [client, setClient] = useState<Client | null>(null);

    useEffect(() => {
        if (!employmentSectorId) return;

        const fetchClient = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/employment-sector/${employmentSectorId}`);
                const employmentSectorData = response.data;
                setClient(new Client(
                    employmentSectorData.id, employmentSectorData.firstName, employmentSectorData.lastName,
                    employmentSectorData.ssn, employmentSectorData.hashed_ssn, employmentSectorData.dob,
                    employmentSectorData.phone, employmentSectorData.email, employmentSectorData.address1,
                    employmentSectorData.address2, employmentSectorData.city, employmentSectorData.state, employmentSectorData.zip, employmentSectorData.employmentSector
                ));
            } catch (error) {
                console.error("Error fetching client details:", error);
            }
        };

        fetchClient();
    }, [employmentSectorId]);

    if (!client) return null;

    return (
        <div className="client-profile">
            <h2>Edit Employment Sector</h2>
            <p><strong>ID:</strong> {client.id}</p>
            <p><strong>Name:</strong> {client.firstName} {client.lastName}</p>
            <p><strong>DOB:</strong> {client.dob.toString()}</p>
            <p><strong>SSN:</strong>     <span className="ssn-container">
                <span className="hidden">{client.ssn}</span>
                <span className="hover"> Show SSN</span>
            </span></p>
            <p>
                <strong>Employment Sector:</strong>
                {client.employmentSector ? client.employmentSector.employmentSectorName : "Not Available"}
            </p>
            <div className="profile-buttons">
                <button className="button-close" onClick={onClose}>Close</button>
                <button className="button-update">Update</button>
            </div>

        </div>
    );
};