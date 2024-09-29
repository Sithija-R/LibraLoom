import React from "react";
import { useNavigate } from "react-router-dom";
import landingImage from "./books3.png";
import logo from "./Logo.png";

export const LandingPage = () => {
  const navigate = useNavigate();

  return (
    <div className="overflow-hidden bg-black text-white h-screen">
      <div
        className="relative h-full"
        style={{
          backgroundImage: `url(${landingImage})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
        }}
      >
        {/* Background Overlay for Darker Effect */}
        <div className="absolute inset-0 bg-black opacity-30" />

        {/* Navbar Section */}
        <nav className="relative flex justify-between items-center px-7 py-5 bg-gradient-to-r from-gray-900 to-black shadow-lg w-full">
          {/* Logo Section */}
          <div className="flex items-center">
            <img src={logo} className="w-32" alt="Logo" />
          </div>

          {/* Sign In & Sign Up Links */}
          <div className="flex space-x-4">
            <span
              onClick={() => navigate("/Authentication/signup")}
              className="bg-gradient-to-r from-gray-900 to-black text-white font-semibold py-2 px-6 rounded-lg transition-all hover:from-gray-700 hover:to-gray-500"
            >
              Sign Up
            </span>
            <span
              onClick={() => navigate("/Authentication/signin")}
              className="bg-gradient-to-r from-gray-900 to-black text-white font-semibold py-2 px-6 rounded-lg transition-all hover:from-gray-700 hover:to-gray-500"
            >
              Sign In
            </span>
          </div>
        </nav>

        {/* Hero Section */}
        <div className="relative  flex justify-center items-center mt-72">
          <div className="text-center">
            <h1 className="text-8xl font-bold mb-6" style={{ fontFamily: 'Cinzel, serif' }}>
              LIBRALOOM
            </h1>
            <p className="text-3xl mb-8" style={{ fontFamily: 'Cinzel, serif' }}>
              Your library, your way 
            </p>
          </div>
        </div>

        {/* Explore Button */}
        <div className="absolute bottom-44 left-0 right-0 flex items-center justify-center z-50">
          <button
            onClick={() => navigate("/Authentication/signin")}
            className="w-44 bg-gradient-to-r from-red-800 to-black text-white font-semibold py-2 px-4 rounded-3xl  hover:from-gray-700 hover:to-gray-500"
          >
            Explore
          </button>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
