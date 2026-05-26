import { useEffect } from 'react';

const ICONS = { success: 'fa-circle-check', error: 'fa-circle-xmark', warning: 'fa-triangle-exclamation', info: 'fa-circle-info' };
const COLORS = {
  success: { bg: 'var(--success-dim)',  border: 'rgba(74,222,128,0.25)',  text: 'var(--success)' },
  error:   { bg: 'var(--danger-dim)',   border: 'rgba(248,113,113,0.25)', text: 'var(--danger)' },
  warning: { bg: 'var(--warning-dim)',  border: 'rgba(251,191,36,0.25)',  text: 'var(--warning)' },
  info:    { bg: 'var(--info-dim)',     border: 'rgba(96,165,250,0.25)',  text: 'var(--info)' },
};

export default function Toast({ toasts, remove }) {
  return (
    <div style={{ position: 'fixed', bottom: 24, right: 24, zIndex: 9999, display: 'flex', flexDirection: 'column', gap: 8, maxWidth: 360 }}>
      {toasts.map(t => (
        <ToastItem key={t.id} toast={t} onRemove={() => remove(t.id)} />
      ))}
    </div>
  );
}

function ToastItem({ toast, onRemove }) {
  const c = COLORS[toast.type] || COLORS.info;

  useEffect(() => {
    const timer = setTimeout(onRemove, 4000);
    return () => clearTimeout(timer);
  }, [onRemove]);

  return (
    <div className="fade-in" style={{
      background: 'var(--bg-elevated)',
      border: `1px solid ${c.border}`,
      borderLeft: `3px solid ${c.text}`,
      borderRadius: 'var(--radius-md)',
      padding: '12px 16px',
      display: 'flex', alignItems: 'flex-start', gap: 10,
      boxShadow: '0 8px 32px rgba(0,0,0,0.4)',
    }}>
      <i className={`fas ${ICONS[toast.type] || ICONS.info}`} style={{ color: c.text, fontSize: 16, marginTop: 1, flexShrink: 0 }} />
      <span style={{ fontSize: 13, color: 'var(--text-primary)', lineHeight: 1.5, flex: 1 }}>{toast.message}</span>
      <button onClick={onRemove} style={{ background: 'none', border: 'none', color: 'var(--text-muted)', padding: 0, fontSize: 14, lineHeight: 1, minWidth: 'auto' }}>
        <i className="fas fa-xmark" />
      </button>
    </div>
  );
}
