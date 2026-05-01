"use client"
import "../../../app/debug.css"
import "./timer-card.css"

import {useRef, useState} from "react";
import {SecondsConverter} from "./SecondsConverter";
import Link from "next/link";
import {Timer} from "@/app/timer/Timer";

export function TimerCard({id, titel, sekunden, aktiv} : Timer) {

    const [isRunning, setIsRunning] = useState<boolean>(false);

    const [thisSeconds, setThisSeconds] = useState<number>(sekunden);

    const intervalRef = useRef<NodeJS.Timeout | null>(null);

    function handleStart() {
        setIsRunning(true);

        intervalRef.current = setInterval(() => {
            setThisSeconds(prev => prev + 1);
        }, 1000);
    }

    async function handleStop() {
        setIsRunning(false);

        if (intervalRef.current) {
            clearInterval(intervalRef.current);
        }

        await fetch("http://localhost:7761/api/v1/timers/" + id + "/sekunden/" + thisSeconds, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        });
    }


    return (
            <div className="timer-card">
                <div className="timer-card-header">
                    <Link id="timer-link" href={`/timer/${id}`} data-testid="timer-link">{titel}</Link>
                </div>
                <div className="timer-card-body">
                    <span data-testid="zeitangabe">Zeit: {SecondsConverter.convert(thisSeconds)}</span>
                </div>
                <div className="timer-card-btn-container">
                    {isRunning && (<button className="btn btn-primary" onClick={handleStop} data-testid="timer-btn">stop</button>)}
                    {!isRunning && (<button className="btn btn-secondary" onClick={handleStart} data-testid="timer-btn">start</button>)}
                </div>
            </div>
        );
}