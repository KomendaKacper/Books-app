import "./App.css";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { HomePage } from "./layouts/HomePage/HomePage";
import { SearchBooksPage } from "./layouts/SearchBooksPage/SearchBooksPage";
import { Navigate, Route, Routes } from "react-router-dom";
import { BookCheckoutPage } from "./layouts/BookCheckoutPage/BookCheckoutPage";
import { Signin } from "./Auth/Signin";

export const App = () => {
  return (
    <div className="d-flex flex-column min-vh-100">
      <Navbar />
      <div className="flex-grow-1">
        <Routes>
          {/* Redirect '/' to '/home' */}
          <Route path="/" element={<Navigate to="/home" />} />

          {/* Home page */}
          <Route path="/home" element={<HomePage />} />

          {/* Search books page */}
          <Route path="/search" element={<SearchBooksPage />} />

          {/* Signin component */}
          <Route path="/signin" element={<Signin />} />

          {/* Book checkout page with dynamic bookId */}
          <Route path="/checkout/:bookId" element={<BookCheckoutPage />} />
        </Routes>
      </div>
      <Footer />
    </div>
  );
};
