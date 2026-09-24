/** @type {import('next').NextConfig} */
const nextConfig = {
  basePath: "/uniaura/app",
  async rewrites() {
    return [
      // --- PROXY PARA A API DO SPRING BOOT, EQUIVALENTE AO server.proxy DO VITE ANTIGO ---
      {
        source: "/api/:path*",
        destination: "http://localhost:8080/api/:path*",
      },
    ];
  },
};

export default nextConfig;
