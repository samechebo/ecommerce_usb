const STATUS_COLORS = {
  ACTIVE:       { bg: 'var(--success-dim)', text: 'var(--success)',  border: 'rgba(74,222,128,0.2)' },
  PAID:         { bg: 'var(--success-dim)', text: 'var(--success)',  border: 'rgba(74,222,128,0.2)' },
  SUCCEEDED:    { bg: 'var(--success-dim)', text: 'var(--success)',  border: 'rgba(74,222,128,0.2)' },
  CHECKED_OUT:  { bg: 'var(--info-dim)',    text: 'var(--info)',     border: 'rgba(96,165,250,0.2)' },
  CREATED:      { bg: 'var(--info-dim)',    text: 'var(--info)',     border: 'rgba(96,165,250,0.2)' },
  CREDIT:       { bg: 'var(--info-dim)',    text: 'var(--info)',     border: 'rgba(96,165,250,0.2)' },
  RESERVE:      { bg: 'var(--warning-dim)', text: 'var(--warning)',  border: 'rgba(251,191,36,0.2)' },
  PENDING:      { bg: 'var(--warning-dim)', text: 'var(--warning)',  border: 'rgba(251,191,36,0.2)' },
  CANCELLED:    { bg: 'var(--danger-dim)',  text: 'var(--danger)',   border: 'rgba(248,113,113,0.2)' },
  ABANDONED:    { bg: 'var(--danger-dim)',  text: 'var(--danger)',   border: 'rgba(248,113,113,0.2)' },
  FAILED:       { bg: 'var(--danger-dim)',  text: 'var(--danger)',   border: 'rgba(248,113,113,0.2)' },
  DEBIT:        { bg: 'var(--danger-dim)',  text: 'var(--danger)',   border: 'rgba(248,113,113,0.2)' },
  RELEASE:      { bg: 'rgba(167,139,250,0.12)', text: '#a78bfa', border: 'rgba(167,139,250,0.2)' },
};

function Badge({ value }) {
  const c = STATUS_COLORS[value];
  if (!c) return <span style={{ color: 'var(--text-primary)', fontSize: 13 }}>{value}</span>;
  return (
    <span style={{
      fontSize: 11, fontWeight: 500, padding: '3px 8px',
      borderRadius: 6, border: `1px solid ${c.border}`,
      background: c.bg, color: c.text, letterSpacing: '0.04em',
    }}>
      {value}
    </span>
  );
}

function BoolBadge({ value }) {
  return (
    <span style={{
      fontSize: 11, fontWeight: 500, padding: '3px 8px', borderRadius: 6,
      background: value ? 'var(--success-dim)' : 'var(--danger-dim)',
      color: value ? 'var(--success)' : 'var(--danger)',
      border: `1px solid ${value ? 'rgba(74,222,128,0.2)' : 'rgba(248,113,113,0.2)'}`,
    }}>
      {value ? 'Sí' : 'No'}
    </span>
  );
}

function CellValue({ k, v }) {
  if (v === null || v === undefined) return <span style={{ color: 'var(--text-muted)', fontSize: 12 }}>—</span>;
  if (typeof v === 'boolean') return <BoolBadge value={v} />;
  const str = String(v);
  if (STATUS_COLORS[str]) return <Badge value={str} />;
  if (k === 'id') return <span style={{ fontFamily: 'monospace', fontSize: 12, color: 'var(--accent-light)', background: 'var(--accent-dim)', padding: '2px 6px', borderRadius: 4 }}>{str}</span>;
  if (str.length > 40) return <span style={{ fontSize: 13, color: 'var(--text-secondary)' }} title={str}>{str.slice(0, 40)}…</span>;
  return <span style={{ fontSize: 13, color: 'var(--text-primary)' }}>{str}</span>;
}

export default function DataTable({ data, onEdit, onDelete, hasUpdate }) {
  if (!data || data.length === 0) {
    return (
      <div style={{ padding: '48px 24px', textAlign: 'center' }}>
        <div style={{ width: 56, height: 56, borderRadius: 16, background: 'var(--bg-elevated)', border: '1px solid var(--border-subtle)', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 16px' }}>
          <i className="fas fa-database" style={{ fontSize: 22, color: 'var(--text-muted)' }} />
        </div>
        <p style={{ color: 'var(--text-muted)', fontSize: 14 }}>Sin registros aún</p>
        <p style={{ color: 'var(--text-muted)', fontSize: 12, marginTop: 4 }}>Crea el primer registro usando el botón Nuevo</p>
      </div>
    );
  }

  const keys = Object.keys(data[0]);

  return (
    <div style={{ overflowX: 'auto' }}>
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ borderBottom: '1px solid var(--border-subtle)' }}>
            {keys.map(k => (
              <th key={k} style={{ padding: '10px 16px', textAlign: 'left', fontSize: 11, fontWeight: 500, color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.08em', whiteSpace: 'nowrap' }}>
                {k}
              </th>
            ))}
            <th style={{ padding: '10px 16px', textAlign: 'right', fontSize: 11, fontWeight: 500, color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.08em' }}>
              Acciones
            </th>
          </tr>
        </thead>
        <tbody>
          {data.map((row, i) => (
            <tr key={i} style={{ borderBottom: '1px solid var(--border-subtle)', transition: 'background 0.1s' }}
              onMouseEnter={e => e.currentTarget.style.background = 'var(--bg-hover)'}
              onMouseLeave={e => e.currentTarget.style.background = 'transparent'}
            >
              {keys.map(k => (
                <td key={k} style={{ padding: '11px 16px', maxWidth: 200, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  <CellValue k={k} v={row[k]} />
                </td>
              ))}
              <td style={{ padding: '11px 16px', textAlign: 'right', whiteSpace: 'nowrap' }}>
                <div style={{ display: 'flex', gap: 6, justifyContent: 'flex-end' }}>
                  {hasUpdate && (
                    <button onClick={() => onEdit(row)} style={{ padding: '5px 10px', fontSize: 12, color: 'var(--accent-light)', borderColor: 'rgba(124,111,247,0.3)', background: 'var(--accent-dim)' }}>
                      <i className="fas fa-pen" />
                    </button>
                  )}
                  <button onClick={() => onDelete(row)} style={{ padding: '5px 10px', fontSize: 12, color: 'var(--danger)', borderColor: 'rgba(248,113,113,0.3)', background: 'var(--danger-dim)' }}>
                    <i className="fas fa-trash" />
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
