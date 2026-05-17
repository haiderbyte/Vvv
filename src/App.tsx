import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "motion/react";
import { Plus, Settings, Check, ArrowLeft, Moon, Compass, Cloud, Waves } from "lucide-react";

const MOOD_ICONS: Record<string, any> = {
  "هادئ": Moon,
  "متأمل": Compass,
  "منعزل": Cloud,
  "غارق في الأفكار": Waves
};

type Mood = "هادئ" | "متأمل" | "منعزل" | "غارق في الأفكار";

interface Entry {
  id: string;
  title: string;
  content: string;
  timestamp: number;
  mood: Mood;
}

const QUOTES = [
  { text: "الإنسان لا يدرك أنه يتنفس إلا حين يختنق.", author: "دوستويفسكي" },
  { text: "كل ما أنا عليه هو نتيجة عزلتي.", author: "فرانز كافكا" },
  { text: "لم يعد بإمكاني العيش مع هذا الضجيج في رأسي.", author: "أوسامو دزاي" },
  { text: "الخجل من الوجود هو قمة الإنسانية.", author: "أوسامو دزاي" },
  { text: "أنا غريب حتى عن نفسي.", author: "فرانز كافكا" },
  { text: "الجحيم هو الآخرون.", author: "جان بول سارتر" }
];

export default function App() {
  const [view, setView] = useState<"home" | "add" | "settings" | "edit">("home");
  const [entries, setEntries] = useState<Entry[]>([]);
  const [editingEntry, setEditingEntry] = useState<Entry | null>(null);
  const [dailyQuote, setDailyQuote] = useState(QUOTES[0]);

  useEffect(() => {
    const dayOfYear = Math.floor((new Date().getTime() - new Date(new Date().getFullYear(), 0, 0).getTime()) / 86400000);
    setDailyQuote(QUOTES[dayOfYear % QUOTES.length] || QUOTES[0]);
    
    const saved = localStorage.getItem("existential_journal_entries");
    if (saved) {
      try {
        setEntries(JSON.parse(saved));
      } catch (e) {
        console.error("Failed to load entries", e);
      }
    }
  }, []);

  const saveEntry = (newEntry: Entry) => {
    let updated;
    if (editingEntry) {
      updated = entries.map(e => e.id === editingEntry.id ? newEntry : e);
    } else {
      updated = [newEntry, ...entries];
    }
    setEntries(updated);
    localStorage.setItem("existential_journal_entries", JSON.stringify(updated));
    setView("home");
    setEditingEntry(null);
  };

  const deleteEntry = (id: string) => {
    const updated = entries.filter(e => e.id !== id);
    setEntries(updated);
    localStorage.setItem("existential_journal_entries", JSON.stringify(updated));
    setView("home");
    setEditingEntry(null);
  };

  return (
    <div className="min-h-screen bg-amoled-black flex flex-col md:flex-row h-screen overflow-hidden selection:bg-white selection:text-black bg-gradient-to-b from-black to-[#080808]">
      {/* Sidebar */}
      <aside className="hidden md:flex w-80 shrink-0 border-l border-dim-gray flex-col p-8 justify-between bg-gradient-to-b from-black via-[#050505] to-[#0A0A0A]">
        <div>
          <div className="mb-8 flex flex-col items-center">
            <img src="input_file_0.png" alt="Existential Journal Logo" className="w-32 h-32 mb-4 object-contain rounded-2xl" />
            <h1 className="font-serif text-3xl tracking-tight bg-clip-text text-transparent bg-gradient-to-l from-white to-white/60">مفكرة الوجود</h1>
          </div>
          
          <div className="p-6 rounded-xl bg-gradient-to-br from-white/[0.04] to-transparent border border-white/[0.08] mb-10 group transition-all hover:border-white/20">
            <p className="font-serif text-xl leading-relaxed italic text-[#E0E0E0]">"{dailyQuote.text}"</p>
            <span className="block mt-4 text-xs font-semibold uppercase tracking-widest text-text-muted">— {dailyQuote.author}</span>
          </div>

          <nav className="space-y-4">
            <p className="text-[10px] uppercase tracking-[0.2em] text-text-muted font-bold mb-6 text-right">الحالات الذهنية</p>
            <div className="grid grid-cols-2 gap-2">
              {Object.entries(MOOD_ICONS).map(([mood, Icon]) => (
                <div key={mood} className="flex flex-col items-center gap-2 p-3 border border-white/10 rounded hover:border-white/30 transition-colors cursor-default group">
                  <Icon size={14} className="text-text-muted group-hover:text-white transition-colors" />
                  <span className="text-[9px] text-text-muted font-bold uppercase tracking-widest">{mood}</span>
                </div>
              ))}
            </div>
          </nav>
        </div>

        <div className="pt-8 border-t border-dim-gray">
          <span className="block font-serif text-lg font-semibold mb-2">حول المطور</span>
          <p className="text-[11px] leading-relaxed text-text-muted text-justify">
            المطور: محمد حيدر (أوسامو دزاي). مطور برمجيات أندرويد ومحاسب ذكي. مؤسس مجتمع "لم يعد إنساناً". صُمم هذا التطبيق ليكون ملاذاً فكرياً نقياً.
          </p>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-1 flex flex-col relative bg-amoled-black overflow-hidden h-full">
        <header className="h-12 shrink-0 px-6 flex justify-between items-center text-[10px] text-text-muted uppercase tracking-widest border-b border-dim-gray md:border-none">
          <span>{new Date().toLocaleTimeString('ar-SA', { hour: '2-digit', minute: '2-digit' })}</span>
          <div className="flex items-center gap-4">
            <span className="flex items-center gap-1.5 text-[9px] font-bold">
              <span className="w-1.5 h-1.5 rounded-full bg-emerald-500 shadow-[0_0_8px_rgba(16,185,129,0.5)]"></span>
              AMOLED ACTIVE
            </span>
            <button onClick={() => setView("settings")} className="md:hidden">
              <Settings size={14} />
            </button>
          </div>
        </header>

        <div className="flex-1 overflow-y-auto custom-scrollbar">
          <AnimatePresence mode="wait">
            {view === "home" && (
              <motion.div 
                key="home"
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -10 }}
                className="p-8 md:p-12 max-w-2xl mx-auto w-full"
              >
                <div className="md:hidden mb-12">
                   <h1 className="font-serif text-4xl mb-6 text-center">مفكرة الوجود</h1>
                   <div className="p-6 rounded-xl bg-white/[0.03] border border-dim-gray italic text-lg leading-relaxed text-[#E0E0E0] text-center font-serif">
                     "{dailyQuote.text}"
                     <span className="block mt-4 text-[10px] not-italic uppercase tracking-widest text-text-muted text-left">— {dailyQuote.author}</span>
                   </div>
                </div>

                <p className="text-[10px] uppercase tracking-[0.2em] text-text-muted font-bold mb-8 border-b border-dim-gray pb-4">التدوينات الأخيرة</p>
                
                {entries.length === 0 ? (
                  <div className="py-20 text-center border border-dashed border-dim-gray rounded-2xl">
                    <p className="text-text-muted/40 font-serif italic">لا توجد تدفّقات فكرية بعد..</p>
                  </div>
                ) : (
                  <div className="space-y-0 text-right">
                    {entries.map((entry) => (
                      <div 
                        key={entry.id} 
                        onClick={() => {
                          setEditingEntry(entry);
                          setView("edit");
                        }}
                        className="group border-b border-dim-gray py-8 last:border-none hover:bg-white/[0.01] transition-colors -mx-4 px-4 rounded-lg cursor-pointer"
                      >
                        <div className="flex items-center gap-4 mb-4">
                          <span className="text-[10px] text-text-muted/60 tracking-wider font-medium">{new Date(entry.timestamp).toLocaleDateString('ar-SA', { day: 'numeric', month: 'long', year: 'numeric' })}</span>
                          <div className="flex items-center gap-1.5 px-2 py-0.5 border border-white/[0.05] rounded-full bg-white/[0.02]">
                            {(() => {
                              const MoodIcon = MOOD_ICONS[entry.mood as string] || Moon;
                              return <MoodIcon size={10} className="text-text-muted" />;
                            })()}
                            <span className="text-[9px] text-text-muted font-bold uppercase tracking-widest">{entry.mood}</span>
                          </div>
                        </div>
                        <h2 className="font-serif text-2xl mb-3 group-hover:text-white transition-colors tracking-tight">{entry.title}</h2>
                        <p className="text-text-muted/80 text-sm leading-relaxed line-clamp-3 text-justify font-light">{entry.content}</p>
                      </div>
                    ))}
                  </div>
                )}
              </motion.div>
            )}

            {(view === "add" || view === "edit") && (
              <motion.div 
                key={view}
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -20 }}
                className="p-8 md:p-12 max-w-2xl mx-auto w-full h-full flex flex-col"
              >
                <header className="flex justify-between items-center mb-12">
                  <button onClick={() => { setView("home"); setEditingEntry(null); }} className="hover:text-white text-text-muted transition-colors flex items-center gap-2">
                    <ArrowLeft size={18} />
                    <span className="text-sm">رجوع</span>
                  </button>
                  <div className="flex items-center gap-4">
                    {view === "edit" && editingEntry && (
                      <button 
                        onClick={() => deleteEntry(editingEntry.id)}
                        className="text-red-500/60 hover:text-red-500 text-xs font-bold transition-colors"
                      >
                        حذف التدوينة
                      </button>
                    )}
                    <button 
                      onClick={() => {
                          const titleInput = document.getElementById('title-input') as HTMLInputElement;
                          const contentInput = document.getElementById('content-input') as HTMLTextAreaElement;
                          const content = contentInput.value;
                          if (!content) return;
                          saveEntry({
                            id: editingEntry?.id || Date.now().toString(),
                            title: titleInput.value || "بدون عنوان",
                            content,
                            timestamp: editingEntry?.timestamp || Date.now(),
                            mood: editingEntry?.mood || "متأمل"
                          });
                      }}
                      className="bg-white text-black px-6 py-2 rounded-full font-bold text-sm flex items-center gap-2 hover:bg-opacity-90 active:scale-95 transition-all shadow-xl shadow-white/10"
                    >
                      <Check size={16} />
                      {view === "edit" ? "تحديث" : "حفظ"}
                    </button>
                  </div>
                </header>

                <div className="flex-1 flex flex-col gap-8">
                  <input 
                    id="title-input"
                    type="text" 
                    defaultValue={editingEntry?.title || ""}
                    placeholder="عنوان الفكرة.." 
                    className="bg-transparent border-none outline-none font-serif text-4xl placeholder:text-white/10 w-full"
                    autoFocus
                  />
                  <textarea 
                    id="content-input"
                    defaultValue={editingEntry?.content || ""}
                    placeholder="اكتب ما يجول في خاطرك هنا.." 
                    className="flex-1 bg-transparent border-none outline-none text-xl leading-relaxed placeholder:text-white/10 resize-none w-full"
                  />
                </div>
              </motion.div>
            )}

             {view === "settings" && (
              <motion.div 
                key="settings"
                initial={{ opacity: 0, scale: 0.98 }}
                animate={{ opacity: 1, scale: 1 }}
                exit={{ opacity: 0, scale: 1.02 }}
                className="p-8 md:p-12 max-w-2xl mx-auto w-full h-full"
              >
                <header className="flex items-center gap-4 mb-12">
                   <button onClick={() => setView("home")} className="hover:text-white text-text-muted transition-colors">
                    <ArrowLeft size={20} />
                  </button>
                  <h2 className="text-xl font-bold">حول التطبيق</h2>
                </header>

                <div className="space-y-12">
                  <section>
                    <h3 className="text-[10px] uppercase tracking-[0.2em] text-text-muted font-bold mb-6">حول المطور</h3>
                    <div className="p-8 border border-dim-gray rounded-2xl bg-white/[0.01] relative overflow-hidden group">
                      <div className="absolute top-0 left-0 w-1 h-full bg-white opacity-20"></div>
                      <h4 className="font-serif text-2xl mb-6">محمد حيدر (أوسامو دزاي)</h4>
                      <p className="text-text-muted text-md leading-relaxed mb-6 text-justify">
                        مطور برمجيات أندرويد ومحاسب ذكي يجمع بين دقة الأرقام الإدارية وإبداع الأكواد البرمجية. مهتم بأتمتة الأنظمة وتطوير البرمجيات المحلية (Local-first) التي تحترم خصوصية المستخدم وأداء الأجهزة.
                      </p>
                      <p className="text-text-muted text-md leading-relaxed text-justify">
                        مؤسس ومشرف على مجتمع وقناة "لم يعد إنساناً" (No Longer Human) الوجودية. تم بناء هذا التطبيق ليكون ملاذاً فكرياً نقياً، بعيداً عن صخب الإنترنت وضجيج الواجهة المزدحمة.
                      </p>
                    </div>
                  </section>
                  
                  <div className="text-center">
                    <p className="text-[10px] text-text-muted/40 uppercase tracking-widest">إصدار 1.0.0 — مفكرة الوجود</p>
                  </div>
                </div>
              </motion.div>
            )}
          </AnimatePresence>
        </div>

        {/* FAB */}
        {view === "home" && (
           <motion.button 
             key="fab"
             initial={{ scale: 0, rotate: -45 }}
             animate={{ scale: 1, rotate: 0 }}
             whileHover={{ scale: 1.1, rotate: 90 }}
             whileTap={{ scale: 0.9 }}
             onClick={() => setView("add")}
             className="fixed md:absolute bottom-8 left-8 md:bottom-12 md:left-12 w-16 h-16 bg-white text-black rounded-full flex items-center justify-center shadow-[0_0_30px_rgba(255,255,255,0.15)] z-50 transition-shadow hover:shadow-[0_0_40px_rgba(255,255,255,0.2)]"
           >
             <Plus size={28} />
           </motion.button>
        )}
      </main>

      <style>{`
        .custom-scrollbar::-webkit-scrollbar {
          width: 4px;
        }
        .custom-scrollbar::-webkit-scrollbar-track {
          background: transparent;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb {
          background: #1a1a1a;
          border-radius: 10px;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb:hover {
          background: #333;
        }
      `}</style>
    </div>
  );
}
