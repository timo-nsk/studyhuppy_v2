import './globals.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Providers} from "@/app/components/Providers";
import {Header} from "@/app/components/layout/header/Header";

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