export const ENTITIES = [
  { key: 'document-type',       label: 'Tipos de documento',  icon: 'fa-id-card',          color: '#7c6ff7' },
  { key: 'user',                label: 'Usuarios',             icon: 'fa-users',             color: '#60a5fa' },
  { key: 'category',            label: 'Categorías',           icon: 'fa-tags',              color: '#34d399' },
  { key: 'product',             label: 'Productos',            icon: 'fa-box',               color: '#f472b6' },
  { key: 'product-category',    label: 'Prod. Categoría',      icon: 'fa-link',              color: '#fb923c' },
  { key: 'inventory',           label: 'Inventario',           icon: 'fa-warehouse',         color: '#a78bfa' },
  { key: 'inventory-movement',  label: 'Movimientos',          icon: 'fa-arrow-right-arrow-left', color: '#2dd4bf' },
  { key: 'cart',                label: 'Carritos',             icon: 'fa-cart-shopping',     color: '#facc15' },
  { key: 'cart-item',           label: 'Items carrito',        icon: 'fa-list',              color: '#f87171' },
  { key: 'order',               label: 'Órdenes',              icon: 'fa-receipt',           color: '#818cf8' },
  { key: 'order-item',          label: 'Items orden',          icon: 'fa-clipboard-list',    color: '#4ade80' },
  { key: 'payment',             label: 'Pagos',                icon: 'fa-credit-card',       color: '#38bdf8' },
];

export const FIELDS = {
  'document-type': {
    create: [
      { name: 'code', label: 'Código', type: 'text', placeholder: 'CC', required: true },
      { name: 'name', label: 'Nombre', type: 'text', placeholder: 'Cédula de Ciudadanía', required: true },
    ],
    update: [
      { name: 'code', label: 'Código', type: 'text', required: true },
      { name: 'name', label: 'Nombre', type: 'text', required: true },
    ],
  },
  user: {
    create: [
      { name: 'fullName',       label: 'Nombre completo',       type: 'text',   placeholder: 'Juan Pérez',        required: true },
      { name: 'phone',          label: 'Teléfono',              type: 'text',   placeholder: '3001234567',        required: true },
      { name: 'email',          label: 'Email',                 type: 'email',  placeholder: 'juan@email.com',    required: true },
      { name: 'documentTypeId', label: 'ID tipo documento',     type: 'number', placeholder: '1',                 required: true },
      { name: 'documentNumber', label: 'Número documento',      type: 'text',   placeholder: '1234567890',        required: true },
      { name: 'birthDate',      label: 'Fecha nacimiento',      type: 'text',   placeholder: '1995-05-20',        required: true },
      { name: 'country',        label: 'País',                  type: 'text',   placeholder: 'Colombia',          required: true },
      { name: 'address',        label: 'Dirección',             type: 'text',   placeholder: 'Calle 123 # 45-67', required: true },
    ],
    update: [
      { name: 'fullName', label: 'Nombre completo', type: 'text',  required: true },
      { name: 'phone',    label: 'Teléfono',        type: 'text',  required: true },
      { name: 'email',    label: 'Email',           type: 'email', required: true },
      { name: 'country',  label: 'País',            type: 'text',  required: true },
      { name: 'address',  label: 'Dirección',       type: 'text',  required: true },
    ],
  },
  category: {
    create: [
      { name: 'name',     label: 'Nombre',                      type: 'text',   placeholder: 'Electrónica', required: true },
      { name: 'parentId', label: 'ID categoría padre (opcional)', type: 'number', placeholder: '' },
    ],
    update: [
      { name: 'name',     label: 'Nombre',                        type: 'text',   required: true },
      { name: 'parentId', label: 'ID categoría padre (opcional)', type: 'number' },
    ],
  },
  product: {
    create: [
      { name: 'name',        label: 'Nombre',              type: 'text',   placeholder: 'Laptop Lenovo',      required: true },
      { name: 'description', label: 'Descripción',         type: 'text',   placeholder: 'Laptop 15 pulgadas' },
      { name: 'price',       label: 'Precio',              type: 'number', placeholder: '2500000',            required: true },
      { name: 'available',   label: 'Disponible (true/false)', type: 'text', placeholder: 'true',            required: true },
    ],
    update: [
      { name: 'name',        label: 'Nombre',              type: 'text',   required: true },
      { name: 'description', label: 'Descripción',         type: 'text' },
      { name: 'price',       label: 'Precio',              type: 'number', required: true },
      { name: 'available',   label: 'Disponible (true/false)', type: 'text', required: true },
    ],
  },
  'product-category': {
    create: [
      { name: 'productId',  label: 'ID producto',  type: 'number', placeholder: '1', required: true },
      { name: 'categoryId', label: 'ID categoría', type: 'number', placeholder: '1', required: true },
    ],
    update: [],
  },
  inventory: {
    create: [
      { name: 'productId', label: 'ID producto', type: 'number', placeholder: '1',  required: true },
      { name: 'stock',     label: 'Stock',       type: 'number', placeholder: '50', required: true },
    ],
    update: [
      { name: 'productId', label: 'ID producto', type: 'number', required: true },
      { name: 'stock',     label: 'Stock',       type: 'number', required: true },
    ],
  },
  'inventory-movement': {
    create: [
      { name: 'productId', label: 'ID producto',               type: 'number', placeholder: '1',      required: true },
      { name: 'orderId',   label: 'ID orden (opcional)',        type: 'number', placeholder: '' },
      { name: 'type',      label: 'Tipo (DEBIT/CREDIT/RESERVE/RELEASE)', type: 'text', placeholder: 'CREDIT', required: true },
      { name: 'qty',       label: 'Cantidad',                  type: 'number', placeholder: '10',     required: true },
    ],
    update: [
      { name: 'qty', label: 'Cantidad', type: 'number', required: true },
    ],
  },
  cart: {
    create: [
      { name: 'userId', label: 'ID usuario', type: 'number', placeholder: '1', required: true },
    ],
    update: [
      { name: 'status', label: 'Estado (ACTIVE / CHECKED_OUT / ABANDONED)', type: 'text', required: true },
    ],
  },
  'cart-item': {
    create: [
      { name: 'cartId',    label: 'ID carrito',  type: 'number', placeholder: '1', required: true },
      { name: 'productId', label: 'ID producto', type: 'number', placeholder: '1', required: true },
      { name: 'quantity',  label: 'Cantidad',    type: 'number', placeholder: '1', required: true },
    ],
    update: [
      { name: 'quantity', label: 'Cantidad', type: 'number', required: true },
    ],
  },
  order: {
    create: [
      { name: 'userId',      label: 'ID usuario', type: 'number', placeholder: '1',      required: true },
      { name: 'totalAmount', label: 'Total',      type: 'number', placeholder: '150000', required: true },
      { name: 'currency',    label: 'Moneda',     type: 'text',   placeholder: 'COP',    required: true },
    ],
    update: [
      { name: 'status', label: 'Estado (CREATED / PAID / CANCELLED)', type: 'text', required: true },
    ],
  },
  'order-item': {
    create: [
      { name: 'orderId',           label: 'ID orden',       type: 'number', placeholder: '1',      required: true },
      { name: 'productId',         label: 'ID producto',    type: 'number', placeholder: '1',      required: true },
      { name: 'quantity',          label: 'Cantidad',       type: 'number', placeholder: '1',      required: true },
      { name: 'unitPriceSnapshot', label: 'Precio unitario', type: 'number', placeholder: '150000', required: true },
    ],
    update: [
      { name: 'quantity', label: 'Cantidad', type: 'number', required: true },
    ],
  },
  payment: {
    create: [
      { name: 'orderId',        label: 'ID orden',           type: 'number', placeholder: '1',             required: true },
      { name: 'providerRef',    label: 'Referencia proveedor', type: 'text', placeholder: 'PAY-ABC123' },
      { name: 'idempotencyKey', label: 'Idempotency Key',    type: 'text',   placeholder: 'uuid-unico-1234', required: true },
    ],
    update: [
      { name: 'status', label: 'Estado (SUCCEEDED / FAILED)', type: 'text', required: true },
    ],
  },
};
