import axios from 'axios';
import React from 'react';
import { EmploymentSector } from '../models/EmploymentSector.ts';
import { useState, useEffect, useRef } from 'react';
import { Capitalize } from '../Capitalize.ts';

export const EmploymentSectors = () => {
    const [employmentSectors, setEmploymentSectors] = useState<EmploymentSector[]>([]);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const getAllEmploymentSectors = async () => {
        try {
            const response = await axios.get("http://localhost:8080/employment-sector");
            setEmploymentSectors(response.data.map((sector: any) => 
                new EmploymentSector(sector.id, sector.employmentSectorName)
            ));
        } catch (error) {
            console.error("Error fetching employment sectors:", error);
        }
    };

    useEffect(() => {
        getAllEmploymentSectors();
    }, []);

    const addFormName: any = useRef('');


    const addEmploymentSector = async( event: any): Promise<void> => {
        event.preventDefault();
        setErrorMessage(null); // Clear any previous error messages

        if (!addFormName.current?.value.trim()) {
            setErrorMessage("Employment sector name cannot be empty.");
            return;
        }

        try {
            await axios.post("http://localhost:8080/employment-sector", {
                employmentSectorName: Capitalize(addFormName.current.value)
            });

            addFormName.current.value = ""; // Clear the input field
            await getAllEmploymentSectors(); // Refresh the table
        } catch (error: any) {
            if (error.response && error.response.status === 409) {
                setErrorMessage("This employment sector already exists.");
            } else {
                setErrorMessage("An error occurred while adding the employment sector.");
                console.error("Error adding employment sector:", error);
            }
        }
    };

    const deleteEmploymentSector = async (id: number) => {
        if (!window.confirm("Are you sure you want to delete this employment sector?")) return;

        try {
            await axios.delete(`http://localhost:8080/employment-sector/${id}`);
            setEmploymentSectors((prevSectors) => prevSectors.filter(sector => sector.id !== id)); 
        } catch (error) {
            console.error("Error deleting employment sector:", error);
            setErrorMessage("Failed to delete employment sector.");
        }
    };

    const updateEmploymentSector = async (id: number) => {
    }

    return (
        <main>
            <h1>Employment Sectors</h1>
            <button>Add Employment Sector</button>
            <form onSubmit={addEmploymentSector}>
                <div>
                    <label htmlFor="addFormName">Name: </label>
                    <input type="text" id="addFormName" name="addFormName" ref={addFormName} />
                </div>
                <input type="submit" value={'Add'}></input>
            </form>
            {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Sector Name</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {employmentSectors.map(sector => (
                        <tr key={sector.id}>
                            <td>{sector.id}</td>
                            <td>{sector.employmentSectorName}</td>
                            <td>
                                <button onClick= {() => updateEmploymentSector(sector.id)}
                                    style={{ background: "none", border: "none", cursor: "pointer" }}
                                >
                                    <img src="/images/pencil.png" alt="Delete" width="20" height="20" />
                                </button>
                            </td>
                            <td>
                                <button 
                                    onClick={() => deleteEmploymentSector(sector.id)} 
                                    style={{ background: "none", border: "none", cursor: "pointer" }}
                                >
                                    <img src="/images/trash-can.png" alt="Delete" width="20" height="20" />
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </main>
    );
};