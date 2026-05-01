import './globals.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Header} from "@/components/layout/header/Header";
import {Providers} from "@/app/components/Providers";

// @ts-expect-error
export default function RootLayout({ children }) {
  return (
      <html lang="de">
          <body>
              <Providers>
                  <Header title="Studyhuppy"></Header>
                  {children}
              </Providers>
          </body>
      </html>
  );
}