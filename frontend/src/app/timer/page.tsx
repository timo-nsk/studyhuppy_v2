"use client"
import "../../app/debug.css"
import "@/app/timer/timer.css"
import {TimerCard} from "@/app/timer/timer-card/TimerCard";
import {useQuery} from '@tanstack/react-query';
import {Timer} from "@/app/timer/Timer";
import {Loading} from "@/app/components/Loading";
import Link from "next/link";

export default function Timers() {

    const timerServicePort = process.env.NEXT_PUBLIC_TIMER_SERVICE_API_PORT

    const { data, error, isPending } = useQuery({
        queryKey: ["timers"],
        queryFn: async () => {
            const res = await fetch(`http://localhost:${timerServicePort}/api/v1/timers`);
            return res.json();
        },
    });

    if (isPending) {
        return (
            <Loading />
        );
    }

    if(error) {
        return (
            <div>
                <h2>Fehler beim Laden der Timer</h2>
                <p>{error.message}</p>
            </div>
        );
    }

    return (
        <main>
            <div>
                <h2>Meine Timer</h2>
            </div>
            <div id="card-container">
                <ul id="fachsemester-ul">
                    <div className="fachsemester-container">
                        <div>
                            {data &&
                                <ul>
                                    {data.map((timer : Timer, j : number) => {
                                        return (
                                            <li key={j}>
                                                <TimerCard
                                                    id={timer.id}
                                                    titel={timer.titel}
                                                    sekunden={timer.sekunden}
                                                    aktiv={timer.aktiv}></TimerCard>
                                            </li>
                                        )
                                    })}
                                </ul>
                            }
                        </div>
                    </div>
                </ul>
            </div>
            <div id="add-container">
                <Link id="add-link" href={'/timer/add'}>+</Link>
            </div>
        </main>
    );

}