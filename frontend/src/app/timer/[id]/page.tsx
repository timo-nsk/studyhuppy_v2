"use client";
import "./timer-details.css"
import "../../debug.css"
import { useParams } from "next/navigation";
import {useQuery} from '@tanstack/react-query';
import {Timer} from "@/app/timer/Timer";
import { useRouter } from "next/navigation";
import {FieldValues, useForm} from "react-hook-form";
import { useQueryClient } from "@tanstack/react-query";

export default function TimerDetails() {

    const params = useParams<{id: string}>();

    const router = useRouter();

    const queryClient = useQueryClient();

    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting, isSubmitSuccessful }
    } = useForm<FieldValues>();

    const { data, error, isPending } = useQuery<Timer>({
        queryKey: ["timer", params.id],
        queryFn: async (): Promise<Timer> => {
            const res = await fetch(
                `http://localhost:7661/api/v1/timers/${params.id}`
            );

            if (!res.ok) {
                throw new Error("Failed to fetch timer");
            }

            return res.json();
        },
    });

    async function handleDelete() {
        console.log("deleting timer");
        fetch(
            `http://localhost:7661/api/v1/timers/${params.id}`,
            {
                method: "DELETE",
            }
        )
            .then(() => {
                console.log("timer deleted");
                queryClient.refetchQueries({ queryKey: ["timers"] });
                router.push("/timer");
            })
    }

    function handleSekundenHinzufuegen(data: FieldValues) {
        if (isNaN(data.sekunden)) {
            console.log("Bitte geben Sie eine gültige Zahl ein!");
            return;
        }
        fetch(
            `http://localhost:7661/api/v1/timers/${params.id}/sekunden/${data.sekunden}`,
            {
                method: "PATCH"
            }
        )
            .then(() => {
                console.log("Sekunden hinzugefügt");
                queryClient.refetchQueries({ queryKey: ["timers"] });
                router.push("/timer")
            })
    }

    return (
        <>
            <div id="timer-details-container">
                <div id="details-container">
                    <div>
                        <h1>Timer &#34;{data?.titel}&#34;</h1>
                    </div>
                        <form id="zeit-hinzufugen-form" onSubmit={handleSubmit(handleSekundenHinzufuegen)}>
                            <div className="row align-items-center mb-3">
                                <div className="col-auto">
                                    <label className="form-label">Zeit (Sekunden):</label>
                                </div>
                                <div className="col-auto">
                                    <input
                                        {...register('sekunden', {required: 'Geben Sie Sekunden an!',
                                            valueAsNumber: true
                                        })}
                                        className="form-control">
                                    </input>
                                </div>
                                <div className="col-auto">
                                    <button className="btn btn-primary">hinzufügen</button>
                                </div>
                            </div>
                        </form>
                    <div>
                        <button className="btn btn-danger" onClick={handleDelete}>Timer löschen</button>
                    </div>
                </div>
            </div>
        </>
    );
}