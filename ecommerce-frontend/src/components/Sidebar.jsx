import { ENTITIES } from '../config/entities';

const GROUPS = [
  { label: 'Catálogo',    keys: ['document-type', 'user', 'category', 'product', 'product-category'] },
  { label: 'Inventario',  keys: ['inventory', 'inventory-movement'] },
  { label: 'Comercio',    keys: ['cart', 'cart-item', 'order', 'order-item', 'payment'] },
];

export default function Sidebar({ active, setActive }) {
  const entityMap = Object.fromEntries(ENTITIES.map(e => [e.key, e]));

  return (
    <aside style={{
      width: 240, flexShrink: 0, height: '100vh', position: 'sticky', top: 0,
      background: 'var(--bg-surface)', borderRight: '1px solid var(--border-subtle)',
      display: 'flex', flexDirection: 'column', overflow: 'hidden',
    }}>
      <div style={{ padding: '28px 24px 20px', borderBottom: '1px solid var(--border-subtle)' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 10 }}>
          <div style={{ width: 34, height: 34, borderRadius: 10, background: 'var(--accent-dim)', border: '1px solid var(--accent-glow)', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <i className="fas fa-store" style={{ color: 'var(--accent)', fontSize: 14 }} />
          </div>
          <div>
            <p style={{ fontSize: 14, fontWeight: 500, color: 'var(--text-primary)', margin: 0 }}>Ecommerce USB</p>
            <p style={{ fontSize: 11, color: 'var(--text-muted)', margin: 0 }}>Panel de gestión</p>
          </div>
        </div>
      </div>

      <nav style={{ flex: 1, overflowY: 'auto', padding: '12px 0' }}>
        {GROUPS.map(group => (
          <div key={group.label} style={{ marginBottom: 8 }}>
            <p style={{ fontSize: 10, fontWeight: 500, color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.1em', padding: '8px 20px 4px', margin: 0 }}>
              {group.label}
            </p>
            {group.keys.map(key => {
              const entity = entityMap[key];
              if (!entity) return null;
              const isActive = active === key;
              return (
                <button
                  key={key}
                  onClick={() => setActive(key)}
                  style={{
                    display: 'flex', alignItems: 'center', gap: 10,
                    width: '100%', padding: '8px 20px',
                    background: isActive ? 'var(--bg-active)' : 'transparent',
                    border: 'none', borderRadius: 0,
                    color: isActive ? 'var(--text-primary)' : 'var(--text-secondary)',
                    fontSize: 13, textAlign: 'left', cursor: 'pointer',
                    transition: 'all 0.15s',
                    borderLeft: isActive ? `2px solid ${entity.color}` : '2px solid transparent',
                    paddingLeft: isActive ? 18 : 18,
                  }}
                >
                  <span style={{
                    width: 28, height: 28, borderRadius: 8, flexShrink: 0,
                    background: isActive ? `${entity.color}22` : 'transparent',
                    display: 'flex', alignItems: 'center', justifyContent: 'center',
                    transition: 'background 0.15s',
                  }}>
                    <i className={`fas ${entity.icon}`} style={{ fontSize: 12, color: isActive ? entity.color : 'var(--text-muted)' }} />
                  </span>
                  {entity.label}
                </button>
              );
            })}
          </div>
        ))}
      </nav>

      <div style={{ padding: '16px 20px', borderTop: '1px solid var(--border-subtle)' }}>
        <a
          href="http://localhost:8080/swagger-ui/index.html"
          target="_blank"
          rel="noreferrer"
          style={{ display: 'flex', alignItems: 'center', gap: 8, fontSize: 12, color: 'var(--text-muted)', textDecoration: 'none', padding: '8px 0' }}
        >
          <i className="fas fa-book-open" style={{ fontSize: 13 }} />
          Swagger UI
          <i className="fas fa-arrow-up-right-from-square" style={{ fontSize: 10, marginLeft: 'auto' }} />
        </a>
      </div>
    </aside>
  );
}
