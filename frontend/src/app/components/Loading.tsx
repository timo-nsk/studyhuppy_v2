export function Loading() {
    return (
        <div className="d-flex justify-content-center">
            <div className="spinner-border text-secondary m-5" role="status">
                <span className="visually-hidden">Loading...</span>
            </div>
        </div>
    );
}