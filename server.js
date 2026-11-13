// API REST propia - Un Finde Tranqui (Grupo1PA3)
// Node.js + Express + JWT. Los datos se guardan en db.json
const express = require("express");
const cors = require("cors");
const jwt = require("jsonwebtoken");
const fs = require("fs");
const path = require("path");
const os = require("os");

const PORT = 3000;
const JWT_SECRET = "uft_grupo1pa3_secreto";
const DB_FILE = path.join(__dirname, "db.json");

// ---------- Base de datos en archivo JSON ----------
const datosIniciales = {
  usuarios: [
    { id: 1, nombre: "Augusto Condorchua", username: "admin", password: "123456" }
  ],
  productos: [
    { id: 1, nombre: "Jägermeister 700 ml", categoria: "Licores", precio: 89.9, stock: 12 },
    { id: 2, nombre: "Smirnoff Vodka 750 ml", categoria: "Vodka", precio: 39.9, stock: 20 },
    { id: 3, nombre: "Ron Cartavio Black 750 ml", categoria: "Ron", precio: 34.9, stock: 15 }
  ],
  nextId: 4
};

function leerDB() {
  if (!fs.existsSync(DB_FILE)) guardarDB(datosIniciales);
  return JSON.parse(fs.readFileSync(DB_FILE, "utf-8"));
}

function guardarDB(db) {
  fs.writeFileSync(DB_FILE, JSON.stringify(db, null, 2));
}

// ---------- App ----------
const app = express();
app.use(cors());
app.use(express.json());

// Muestra en consola cada petición que llega
app.use((req, res, next) => {
  console.log(`${new Date().toLocaleTimeString()}  ${req.method} ${req.originalUrl}`);
  next();
});

app.get("/", (req, res) => {
  res.json({ mensaje: "API Un Finde Tranqui funcionando" });
});

// ---------- Login: genera el token JWT ----------
app.post("/api/auth/login", (req, res) => {
  const { username, password } = req.body || {};
  if (!username || !password) {
    return res.status(400).json({ mensaje: "Usuario y contraseña son obligatorios" });
  }
  const db = leerDB();
  const usuario = db.usuarios.find(u => u.username === username && u.password === password);
  if (!usuario) {
    return res.status(401).json({ mensaje: "Usuario o contraseña incorrectos" });
  }
  const token = jwt.sign(
    { id: usuario.id, username: usuario.username, nombre: usuario.nombre },
    JWT_SECRET,
    { expiresIn: "8h" }
  );
  res.json({
    token,
    usuario: { id: usuario.id, nombre: usuario.nombre, username: usuario.username }
  });
});

// ---------- Middleware: valida "Authorization: Bearer <token>" ----------
function verificarToken(req, res, next) {
  const [tipo, token] = (req.headers.authorization || "").split(" ");
  if (tipo !== "Bearer" || !token) {
    return res.status(401).json({ mensaje: "Token no enviado" });
  }
  try {
    req.usuario = jwt.verify(token, JWT_SECRET);
    next();
  } catch (e) {
    return res.status(401).json({ mensaje: "Token inválido o expirado" });
  }
}

// ---------- CRUD Productos (requieren token) ----------
function validarProducto(body) {
  const { nombre, categoria, precio, stock } = body || {};
  if (!nombre || !String(nombre).trim()) return "El nombre es obligatorio";
  if (!categoria || !String(categoria).trim()) return "La categoría es obligatoria";
  if (precio === undefined || isNaN(Number(precio)) || Number(precio) < 0) return "Precio inválido";
  if (stock === undefined || !Number.isInteger(Number(stock)) || Number(stock) < 0) return "Stock inválido";
  return null;
}

app.get("/api/productos", verificarToken, (req, res) => {
  res.json(leerDB().productos);
});

app.get("/api/productos/:id", verificarToken, (req, res) => {
  const producto = leerDB().productos.find(p => p.id === Number(req.params.id));
  if (!producto) return res.status(404).json({ mensaje: "Producto no encontrado" });
  res.json(producto);
});

app.post("/api/productos", verificarToken, (req, res) => {
  const error = validarProducto(req.body);
  if (error) return res.status(400).json({ mensaje: error });
  const db = leerDB();
  const nuevo = {
    id: db.nextId++,
    nombre: String(req.body.nombre).trim(),
    categoria: String(req.body.categoria).trim(),
    precio: Number(req.body.precio),
    stock: Number(req.body.stock)
  };
  db.productos.push(nuevo);
  guardarDB(db);
  res.status(201).json(nuevo);
});

app.put("/api/productos/:id", verificarToken, (req, res) => {
  const error = validarProducto(req.body);
  if (error) return res.status(400).json({ mensaje: error });
  const db = leerDB();
  const i = db.productos.findIndex(p => p.id === Number(req.params.id));
  if (i === -1) return res.status(404).json({ mensaje: "Producto no encontrado" });
  db.productos[i] = {
    id: db.productos[i].id,
    nombre: String(req.body.nombre).trim(),
    categoria: String(req.body.categoria).trim(),
    precio: Number(req.body.precio),
    stock: Number(req.body.stock)
  };
  guardarDB(db);
  res.json(db.productos[i]);
});

app.delete("/api/productos/:id", verificarToken, (req, res) => {
  const db = leerDB();
  const i = db.productos.findIndex(p => p.id === Number(req.params.id));
  if (i === -1) return res.status(404).json({ mensaje: "Producto no encontrado" });
  db.productos.splice(i, 1);
  guardarDB(db);
  res.json({ mensaje: "Producto eliminado" });
});

// ---------- Inicio ----------
app.listen(PORT, "0.0.0.0", () => {
  console.log(`API Un Finde Tranqui corriendo en el puerto ${PORT}`);
  console.log("Copia una de estas URLs en RetrofitClient.BASE_URL:");
  for (const lista of Object.values(os.networkInterfaces())) {
    for (const ip of lista || []) {
      if (ip.family === "IPv4" && !ip.internal) console.log(`   http://${ip.address}:${PORT}/`);
    }
  }
});