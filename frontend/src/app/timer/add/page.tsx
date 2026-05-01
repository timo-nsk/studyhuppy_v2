"use client"
import "../../debug.css"
import "./add-timer.css"
import {FieldValues, useForm} from "react-hook-form";
import {useState} from "react";
import { useQueryClient } from "@tanstack/react-query";

export default function AddTimer() {

    const [responseOk, setResponseOk] = useState(false);

    const queryClient = useQueryClient();

    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting, isSubmitSuccessful }
    } = useForm<FieldValues>();

    async function handleForm(data: FieldValues) {

        const response = await fetch("http://localhost:7761/api/v1/timers", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                titel: data.titel
            })
        });

        setResponseOk(response.ok);
        queryClient.refetchQueries({ queryKey: ["timers"] });
    }

    return (
        <div id="add-timer-container">
            <h2>Neuen Timer erstellen</h2>
            <div id="form-container">
                <form id="add-timer-form" onSubmit={handleSubmit(handleForm)}>
                    <div className="row align-items-center mb-3">
                        <div className="col-auto">
                            <label className="form-label">Titel</label>
                        </div>
                        <div className="col-auto">
                            <input
                                className="form-control" {...register('titel', {required: 'Geben Sie einen Titel ein!'})} />
                        </div>
                        <div className="col-auto">
                            <span className="error">{errors.titel?.message && <div>{errors.titel.message as string}</div>}</span>
                        </div>
                    </div>
                    <div className="mb-3">
                        <button className="btn btn-secondary" type="submit">
                            {isSubmitting && (<span>Timer hinzugefügen...</span>)}
                            hinzufügen
                        </button>
                    </div>
                    {isSubmitSuccessful && responseOk && (<div className="alert alert-success" role="alert">Timer erfolgreich hinzugefügt!</div>)}
                    {isSubmitSuccessful && !responseOk && (<div className="alert alert-warning" role="alert">Fehler bei der Timer-Erstellung</div>)}
                </form>
            </div>
        </div>
    )
}