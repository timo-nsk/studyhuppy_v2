"use client";

import {HeaderProps} from "./HeaderProps";
import "../../../debug.css"
import "./header.css"
import Link from "next/link";
import {usePathname} from "next/navigation";

export function Header({title} : HeaderProps) {

    const pathname = usePathname()

    return (
        <header>
            <div>
                <h1>{title}</h1>
            </div>
            <nav id="navbar">
                <ul>
                    <li className="nav-li">
                        <Link className={`nav-a ${pathname === '/' ? 'active' : ''}`}  href="/">Home</Link>
                    </li>
                    <li className="nav-li">
                        <Link className={`nav-a ${pathname === '/timer' ? 'active' : ''}`} href={'/timer'}>Timer</Link>
                    </li>
                </ul>
            </nav>
        </header>
    );
}