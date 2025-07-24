package com.example.bookworm.data

import com.example.bookworm.model.Book

// In DataSource.kt

object DataSource {
    fun getPredefinedBooks(packageName: String): List<Book> {
        fun resourceUri(resourceName: String) = "android.resource://$packageName/drawable/$resourceName"

        return listOf(
            Book(
                title="Echoes And Empire",
                author="Morgan Rhodes",
                year=2022,
                genre="Fictional",
                review="""In a world haunted by the ghosts of a fallen empire, where whispers of old magic and forgotten power linger in the shadows, an ordinary young woman's life is irrevocably shattered. The mysterious disappearance of her family leaves her adrift in a sea of uncertainty and danger. Driven by a desperate need for answers and a fierce loyalty to those she has lost, she embarks on a perilous quest that will force her to confront the grim realities of her society and the hidden truths about her own lineage.
Her search for the truth leads her to the doorstep of a clandestine organization known only to a select few—a secret society of spies and assassins who operate in the murky underworld of political intrigue. To have any hope of finding her family, she is given an ultimatum: join their ranks and become one of them. She must trade her identity for a mask, her innocence for a blade, and learn the deadly arts of espionage, deception, and combat, all while navigating allegiances with individuals as dangerous as they are charismatic.
As she delves deeper into this web of secrets, she discovers that her family's fate is intrinsically linked to the very empire that crumbled generations ago. The echoes of the past are not dead; they are a powerful force threatening to resurface, and she may be the key to either restoring balance or plunging the world into a new era of darkness. From the New York Times bestselling author of the Falling Kingdoms series, this novel is a masterful blend of high-stakes fantasy and thrilling espionage, creating a narrative rich with political maneuvering, unexpected betrayals, and the journey of a heroine finding her strength in a world designed to break her.""",
                imageUri=resourceUri("echoes_and_empire")),
            Book(
                title="The Crimson Moth",
                author="Kristen Ciccarelli",
                year=2024,
                genre="Fictional",
                review="""In a realm teetering on the edge of ruin, an encroaching darkness threatens to swallow the last vestiges of light and hope. The kingdom's only chance for survival lies with a young woman who carries a heavy and dangerous secret: she is the last of her kind, wielding a potent and forbidden magic that society has long feared and sought to eradicate. Her existence is a paradox, for the very power that could save them all is wild and volatile, threatening to consume her from within.
Burdened by this legacy, she must embark on a perilous journey not only across a treacherous landscape but also into the depths of her own untamed abilities. To protect her world, she must learn to command the raw, chaotic energy that flows through her veins before it destroys her. This quest demands immense sacrifice, forcing her to confront ancient evils and make choices that will forever alter the course of her destiny and the fate of her kingdom.
Her path becomes further complicated when she encounters an adversary whose own goals are entwined with hers, leading to a fragile and dangerous alliance. The tension between them ignites an unexpected and passionate romance, adding a layer of personal turmoil to her epic mission. For fans of Leigh Bardugo and Sarah J. Maas, this is a thrilling fantasy romance where love and duty collide, and where a young woman must harness her inner fire to fight the encroaching shadows, even if it means becoming the monster everyone has always feared.""",
                imageUri=resourceUri("the_crimson_moth")),
            Book(
                title="The Lottery",
                author="Shirley Jackson",
                year=1948,
                genre="Fictional",
                review="""Shirley Jackson's masterpiece, "The Lottery," opens on a pristine June morning in a quintessential small American town. The sun is shining, flowers are blooming, and the villagers are gathering in the square between the post office and the bank for their annual tradition. The atmosphere is deceptively cheerful, filled with neighborly small talk and the innocent play of children, who are already stuffing their pockets with stones. This seemingly peaceful and idyllic setting masterfully conceals the sinister undercurrent of the day's event, building a sense of unease that slowly tightens its grip on the reader.
The ritual is presided over with a solemn, almost bureaucratic air, centered around a shabby, splintered black box that holds the slips of paper. As the head of each household is called forward to draw, the veneer of casual community begins to crack, revealing a deep-seated anxiety. The lottery is a tradition so ancient that its original purpose and many of its rituals have been lost to time, yet the townspeople cling to it with a fierce, unquestioning loyalty. Jackson masterfully illustrates the concept of blind conformity, showing how ordinary, seemingly good people can perpetuate a horrifying practice simply because "it's always been done this way."
The story builds to a horrifying and unforgettable climax when the "winner" is declared. The community's mood shifts in an instant from nervous anticipation to a cold, unified resolve. In a shocking act of ritualistic violence, the chosen person is set upon by their friends, neighbors, and even family, revealing the terrifying darkness that can lurk beneath the surface of civilized society. "The Lottery" is not just a short story; it is a chilling and timeless allegory that serves as a stark commentary on the dangers of unthinking tradition and the capacity for cruelty within all of us.""",
                imageUri=resourceUri("the_lottery")),
            Book(
                title="Exquisite Ruin",
                author="Andrianne May",
                year=2025,
                genre="Fictional",
                review="""In the heart of a once-great city now lost to time, a dedicated historian arrives to sift through the rubble of its demise. The city is a hauntingly beautiful paradox of opulent decay, where crumbling marble halls and faded frescoes whisper tales of a vibrant past. The historian's mission is to uncover the truth behind the city’s sudden and catastrophic fall, a historical enigma that has puzzled scholars for generations. She believes the key lies not in records of war or famine, but somewhere hidden within the silent, ghostly architecture.
Her breakthrough comes in an unexpected place: a hidden compartment containing a collection of beautifully preserved letters. As she begins to read, a story of intense and forbidden love unfolds, transporting her back to the city's golden age. The letters document a passionate, secret affair between two people whose love was a dangerous defiance of the rigid social and political structures of their time. They write of stolen moments in moonlit gardens and the constant fear of being discovered, weaving a narrative that is both deeply personal and dangerously political.
The historian soon realizes she has stumbled upon more than just a tragic romance. This love affair was at the epicenter of a web of political intrigue, betrayal, and rebellion. The letters reveal a single, devastating choice made by the lovers—an act of desperation that inadvertently set in motion the very events that led to the city's spectacular downfall. "Exquisite Ruin" is a story of how the most intimate human emotions can shape the grand sweep of history, and how a love powerful enough to defy an era was also potent enough to destroy it.""",
                imageUri=resourceUri("exquisite_ruin")),
            Book(
                title="Of Blood And Aether",
                author="Harper Hawthorne",
                year=2024,
                genre="Fictional",
                review="""In a world that is slowly bleeding its magic, the ancient power of aether is fading into memory. The land grows dull and life becomes harder as the vibrant energy that once sustained it trickles away, leaving humanity on the brink of a mundane and powerless future. Legends, however, speak of one person who might be able to reverse the decline: a powerful and reclusive sorceress living in self-imposed exile, a woman who is rumored to hold the key not just to magic's existence, but to its volatile and untamed return.
Her carefully constructed solitude is shattered by the arrival of a desperate prince, a man driven by the imminent collapse of his kingdom. He seeks her out as a last resort, pleading with her to use her knowledge to save their world. But his request forces her to confront the very past she fled from—a history filled with pain, loss, and a terrifying display of her own power that led to her isolation. For her, magic is not a gift but a burden, and its return could unleash the same chaos she once caused.
She is faced with an impossible choice: remain hidden and allow the world to wither and die, or answer the call for aid and risk unleashing a cataclysmic power she may not be able to control. She must weigh the fate of a world she has forsaken against the personal demons that haunt her. This epic fantasy delves into themes of responsibility, redemption, and the profound question of whether a world that has wronged you is still a world worth saving.""",
                imageUri=resourceUri("of_blood_and_aether")),
            Book(
                title="You've Reached Sam",
                author="Dustin Thao",
                year=2021,
                genre="Romance",
                review="""Seventeen-year-old Julie is completely shattered by the sudden, tragic death of her boyfriend, Sam. Theirs was a love that felt infinite, filled with shared dreams of escaping their small town and building a future together. Now, that future is gone, and Julie is lost in a fog of grief so thick she can barely function. She skips his funeral, purges her room of his belongings, and desperately tries to erase every memory, hoping to erase the pain. But the ache of his absence is a constant, haunting presence.
In a moment of overwhelming desperation, wanting nothing more than to hear his voice one last time, Julie calls Sam's cell phone, expecting to be sent to his voicemail. To her utter disbelief, Sam picks up. A miraculous, impossible connection is forged across the boundary between life and death, giving Julie a second chance to speak with the boy she loves. Their calls become her precious secret, a lifeline that allows her to hold onto him and escape the painful reality of her new life without him.
While this magical connection provides temporary solace, it also keeps Julie tethered to the past, preventing her from truly grieving and moving forward. She finds herself withdrawing from friends and family, living for these stolen moments on the phone while her real life passes her by. This emotional and heartfelt debut novel by Dustin Thao is a poignant exploration of love and loss, capturing the heart-wrenching struggle of learning to let go. It poses the difficult question: if you had one more chance to say goodbye, would you ever be able to hang up?""",
                imageUri=resourceUri("youve_reached_sam")),
            Book(
                title="Always isn't Forever",
                author="J.C. Cervantes",
                year=2023,
                genre="Romance",
                review="""For one young couple, "always" was a promise whispered under starry skies and a future they were building together, day by day. Their first love was an all-consuming force, a universe of inside jokes, shared dreams, and the unshakeable certainty that their bond was unbreakable. They were on the cusp of everything, believing their forever was just beginning, a path they would walk side-by-side without end.
But a single, devastating moment shatters their perfect world when a tragic accident rips them apart. Left behind, the surviving partner is plunged into a silent, grief-stricken existence where every memory is a painful reminder of what has been lost. Their bond, however, refuses to be severed by the finality of death. It endures in whispers on the wind, in shared memories that feel startlingly present, and in a connection that defies logical explanation, testing the very boundaries between life and what comes after.
This heartfelt and poignant story from J.C. Cervantes delves into the very definition of "forever." It explores the enduring power of memory and asks if love can truly transcend the physical realm. The protagonist must navigate a world that has lost its color while grappling with a love that refuses to fade, forcing them to confront the nature of grief, healing, and the difficult process of honoring the past while learning to live in the present. It's a deeply emotional journey that questions whether "always" has to end, or if it simply changes form.""",
                imageUri=resourceUri("always_isnt_forever")),
            Book(
                title="Our Cursed Love",
                author="Julie Abe",
                year=2023,
                genre="Romance",
                review="""In a vibrant, magical world, two ambitious young people are known for one thing: their fierce and unrelenting rivalry. They are competitors in everything, constantly trying to one-up each other in skill, wit, and magical prowess. Their animosity is public knowledge, a source of entertainment and gossip for those around them. They believe they have each other figured out, seeing only the sharp edges of their opponent's ambition and drive, never looking for the person underneath.
Their lives are thrown into chaos when they become bound by a powerful, ancient curse. This magical tether forces them into unwilling proximity, compelling them to work together to find a way to break it. Suddenly, the rivals who couldn't stand to be in the same room must now cooperate to survive, navigating a series of magical trials and shared dangers. Their forced alliance is fraught with tension, bickering, and the constant struggle between their ingrained hostility and their shared goal.
As they spend more time together, however, the layers of their rivalry begin to peel away, revealing surprising vulnerabilities and unexpected strengths. Grudging respect slowly blossoms into something deeper and far more confusing: genuine affection. The most pressing question is no longer just how to break the curse, but what will happen to them if they do. They must race against time to free themselves, all while wondering if the love sparking between them is real or simply a side effect of the magic that binds them.""",
                imageUri=resourceUri("our_cursed_love")),
            Book(
                title="Little Do You Know",
                author="Laura Matthews",
                year=2018,
                genre="Romance",
                review="""For two childhood friends, their bond has always been the one constant in a changing world. They've shared everything—secrets, laughter, and years of memories—creating a friendship so close they believe there are no surprises left between them. As they enter their final year of high school, a time filled with the promise of new beginnings and the bittersweet finality of endings, the comfortable foundation of their relationship is about to be tested in ways they never imagined.
Beneath the surface of their easy camaraderie lie unspoken feelings and long-buried truths. One has harbored a secret crush for years, mistaking deep love for simple friendship, while the other has been hiding a painful secret about their family life, desperate to maintain a facade of normalcy. As the pressures of their senior year mount, these hidden aspects of their lives begin to bubble to the surface, creating cracks in the perfect friendship they thought they had.
The slow unraveling of these secrets forces them to see each other in a completely new light. They must confront the awkward, vulnerable truth of their feelings and the pain of past deceptions. This revelation could be the very thing that tears their lifelong friendship apart, leaving them with nothing but regret. Or, it could be the catalyst that allows them to build something new and more profound, transforming their bond from friendship into a love that was there all along, just waiting to be acknowledged.""",
                imageUri=resourceUri("little_do_you_know")),
            Book(
                title="Show Me the Way",
                author="A.L. Jackson",
                year=1975,
                genre="Romance",
                review="""This classic romance is set in a small, picturesque town where life moves at a slower pace and everyone believes they know their neighbors' business. At the heart of this community is a dedicated small-town lawyer, a figure of stability and reason. This lawyer's life is orderly and predictable, built on a foundation of logic and a firm belief in the way things ought to be. They are content, settled, and certain they have everything figured out.
This comfortable equilibrium is disrupted by the arrival of a mysterious newcomer. This stranger carries an air of secrets and a past they refuse to discuss, immediately becoming the subject of town gossip and suspicion. The lives of the lawyer and the newcomer intersect, perhaps through a legal entanglement or a chance encounter that neither can ignore. Their initial interactions are marked by caution and curiosity, a dance between the lawyer's need for answers and the newcomer's desire to remain hidden.
As they are drawn further into each other's orbits, a powerful and unexpected connection begins to form. The newcomer challenges the lawyer's rigid worldview, while the lawyer offers a sense of trust and stability that the newcomer has long been missing. It is a story about finding direction and love in the most unlikely of circumstances, exploring how two very different people can challenge everything the other believes, and in doing so, show each other the way to a more fulfilling future.""",
                imageUri=resourceUri("show_me_the_way")),
            Book(
                title="The Strange Worlds",
                author="L.D. Lapinski",
                year=2020,
                genre="Adventure",
                review="""Welcome to the Strangeworlds Travel Agency, a dusty, unassuming shop that holds the greatest secret in the multiverse. This is no ordinary travel agency; it doesn't book flights or holidays. Instead, it offers journeys to other worlds through a collection of magical suitcases, each one a portal to a different, fantastical reality. When a group of curious young travelers stumbles upon the agency, they are initiated into a world of breathtaking adventure and infinite possibility.
Their initial excitement and wonder, however, quickly turn into a high-stakes mission. They discover that the very fabric of reality connecting these parallel universes is beginning to fray. Worlds are colliding, magic is becoming unstable, and a mysterious force is threatening to unravel everything. The young adventurers realize that they are the only ones who understand the scale of the danger and have the means to stop it, turning their exploration into a desperate race against time.
Guided by the quirky and cryptic rules of the Strangeworlds Travel Agency, the group must journey from one bizarre world to the next. Each suitcase opens into a new land with its own unique creatures, cultures, and dangers—from shimmering crystal forests to cities built on the backs of giant beasts. They must use their wits, courage, and growing friendship to mend the tears in reality before their own world, and all others, are lost forever.""",
                imageUri=resourceUri("the_strange_worlds")),
            Book(
                title="Into The Windwracked Wilds",
                author="A. Deborah Baker",
                year=2022,
                genre="Adventure",
                review="""This standalone fantasy transports readers to a land that defies all maps and logic. The Windwracked Wilds are a place of impossible geography, where mountains might move overnight and rivers change course with the mood of the sky. It is a realm of untamed, ancient magic, inhabited by mythical creatures and governed by the whims of nature itself. This is a world that does not welcome travelers, a place where only the brave or the foolish dare to tread.
At the center of the story is an unlikely hero, not a fated warrior or a powerful mage, but an ordinary person who is thrust into an extraordinary situation. Whether by accident or by a desperate need, this protagonist must leave their quiet life behind and embark on a perilous journey through this treacherous and unpredictable landscape. With no clear path and only their own resolve to guide them, they must face the wonders and terrors of the Wilds.
"Into the Windwracked Wilds" is a lyrical and deeply imaginative tale of courage, resilience, and self-discovery. The journey is as much an internal one as it is external, as the hero must find a strength they never knew they possessed to survive. It is a story that celebrates the beauty of the untamed world and the profound transformation that occurs when one steps off the beaten path and into the heart of the unknown.""",
                imageUri=resourceUri("into_the_windwracked_wilds")),
            Book(
                title="The Beast Warrior",
                author="Nahoko Uehashi",
                year=1990,
                genre="Adventure",
                review="""In a land perpetually scarred by war, where kingdoms clash and the lives of ordinary people are caught in the balance, a young warrior embarks on a journey that will challenge the very nature of the conflict. The world is defined by deep-seated animosity and the brutal realities of combat, and our hero is a product of this harsh environment, trained for a life of struggle and duty.

Everything changes when this warrior encounters and forms an unprecedented bond with a fearsome, magnificent beast—a creature of immense power that others view with terror and superstition. This connection is not one of master and servant, but a deep, symbiotic relationship built on mutual respect and understanding. Together, they become a legendary force, a unique combination of human strategy and wild, untamable power that begins to shift the tides of the war in unexpected ways.

This epic fantasy from Nahoko Uehashi is more than just a tale of battles and alliances. It is a profound exploration of humanity's complex relationship with the natural world, the senselessness of war, and the possibility of finding harmony in the most unlikely of partnerships. The bond between warrior and beast becomes a powerful symbol of a different way forward, questioning whether the cycles of violence can be broken by connecting with the wild, untamed spirit of nature itself.""",
                imageUri=resourceUri("the_beast_warrior")
            ),
            Book(
                title="The Hurricane Wars",
                author="Thea Guanzon",
                year=2023,
                genre="Adventure",
                review="""Set in a breathtaking world inspired by Southeast Asian mythology, this story unfolds in a realm perpetually ravaged by catastrophic storms. For generations, two rival nations have been locked in a bitter and exhausting war, their conflict as relentless as the magical hurricanes that shape their land. Amidst this chaos, two individuals stand on opposite sides, born as enemies and trained to despise one another with every fiber of their being.

Their long-standing animosity is suddenly rendered insignificant by the emergence of a greater, world-ending threat. To have any chance of survival, these two sworn enemies must forge a fragile and dangerous alliance. Bound by a common goal but divided by a lifetime of hatred, they must navigate a treacherous path of mistrust and political intrigue. Every shared glance and reluctant agreement is fraught with tension as they struggle to work together against the forces that would see their entire world destroyed.

As they fight side-by-side, the lines between duty and desire begin to blur. Their forced proximity ignites an intense and unexpected romance, complicating their mission and their allegiances. An epic fantasy romance filled with powerful magic, high-stakes action, and intricate political maneuvering, "The Hurricane Wars" is a thrilling tale of how love can blossom in the heart of conflict, and how two enemies must learn to trust each other to save everything they know.""",
                imageUri=resourceUri("the_hurricane_wars")
            ),
            Book(
                title="Moonchild Voyage of the Lost and Found",
                author="Aisha Bushby",
                year=2020,
                genre="Adventure",
                review="""This enchanting middle-grade adventure begins with a young protagonist known as a "Moonchild," who feels adrift in the world. Haunted by a sense of incompleteness, they are driven by a singular, powerful motivation: to find the family they have lost. The journey ahead is not across familiar lands but across the vast, mysterious expanse of the open ocean, a place of both wonder and danger.
The voyage is guided not by conventional maps, but by the subtle, magical language of the world itself. The Moonchild must learn to read the stories written in the stars and to listen to the secrets whispered by the ocean currents. The sea is a living, breathing character in this tale, offering clues and challenges in equal measure. This magical guidance makes the journey a whimsical and lyrical experience, filled with moments of awe and discovery.
At its heart, "Moonchild Voyage of the Lost and Found" is a deeply moving story about hope, belonging, and the search for one's place in the world. The sea-faring quest becomes a powerful metaphor for finding oneself and understanding where one comes from. It's a tale that captures the magic of the sea and the resilience of the human spirit, perfect for young readers who dream of adventure and believe in the power of hope to guide them home.""",
                imageUri=resourceUri("moonchild_voyage_of_the_lost_and_found")),
            Book(
                title="Power of your Subconscious Mind",
                author="Dr. Joseph Murphy",
                year=1963,
                genre="Self Help",
                review="""Dr. Joseph Murphy's "Power of Your Subconscious Mind" is a landmark text in personal development, built on a simple but revolutionary premise: the greatest power you will ever know lies within your own mind. The book explains that beneath our everyday conscious thoughts, there is a vast, powerful subconscious that shapes our beliefs, habits, and ultimately, our reality. This foundational guide teaches that by understanding and directing this inner force, we can fundamentally change our external circumstances.
This is not a book of abstract philosophy, but a manual for practical application. Dr. Murphy provides a wealth of simple, effective techniques—including affirmations, visualization, and prayer—designed to reprogram the subconscious. He demonstrates how to remove mental blocks that lead to failure and unhappiness, and how to replace them with thought patterns that attract success, wealth, health, and harmonious relationships. The core message is that whatever the conscious mind believes and feels to be true, the subconscious mind will accept and bring to pass.
By harnessing this incredible mental power, readers can unlock their true potential and begin to achieve their most cherished goals. Dr. Murphy's timeless wisdom offers a direct path to mastering your own mind to create a life filled with happiness, prosperity, and peace. It's an empowering guide that has inspired millions to stop being passive observers of their lives and become the conscious creators of their own destiny.""",
                imageUri=resourceUri("power_of_your_subconscious_mind")),
            Book(
                title="Winning The War in your Mind",
                author="Craig Groeschel",
                year=2021,
                genre="Self Help",
                review="""This book presents a powerful and resonant metaphor for the internal struggles many of us face: your mind is a battlefield. Craig Groeschel argues that the most important battles we fight are not external, but internal, waged against the destructive forces of negative thinking, self-doubt, and fear. Our lives are often shaped by the outcome of this war, and winning it is the key to unlocking a life of fulfillment.
Groeschel provides a practical, strategic arsenal for victory. The first step is to learn how to identify the toxic thought patterns—the "lies"—that have been holding you captive. These are the ingrained, negative beliefs that dictate your reactions and limit your potential. Once identified, the book equips you with the tools to systematically replace these lies with empowering truths, drawing on principles of cognitive psychology and faith to "rewire" your brain for a healthier, more positive outlook.
The ultimate goal of this mental warfare is not just to find peace, but to live a life of purpose and joy. By taking control of your thought life, you can break free from the cycles of anxiety and negativity that hold you back. "Winning the War in Your Mind" is a hands-on, encouraging guide for anyone ready to stop being a prisoner of their own thoughts and start actively building a life aligned with their true values and aspirations.""",
                imageUri=resourceUri("winning_the_war_in_your_mind")),
            Book(
                title="Atomic Habits",
                author="James Clear",
                year=2018,
                genre="Self Help",
                review="""James Clear’s "Atomic Habits" presents a revolutionary approach to personal change that has resonated with millions. The core idea is that real, lasting transformation comes not from dramatic, sweeping changes, but from the compound effect of hundreds of small, incremental decisions. Clear introduces the concept of "atomic habits"—tiny, easy-to-implement routines that, when performed consistently, accumulate into remarkable results over time. He argues that we should focus less on ambitious goals and more on building better systems.

The book offers a highly practical and actionable framework, known as the Four Laws of Behavior Change: make it obvious, make it attractive, make it easy, and make it satisfying. For each law, Clear provides a set of simple rules and strategies to help you build good habits and, by inverting the laws, break bad ones. This isn't about mustering superhuman willpower; it's about designing your environment and your routines in a way that makes positive behaviors effortless and negative ones difficult.

Drawing on inspiring stories from business leaders, artists, and athletes, as well as cutting-edge science from biology and psychology, Clear explains the 'why' behind habit formation. He demystifies the process, making self-improvement accessible to everyone. "Atomic Habits" is the definitive guide for anyone looking to make small, consistent improvements every day, proving that tiny adjustments in our behavior can lead to a life of extraordinary achievement and fulfillment.""",
                imageUri=resourceUri("atomic_habits")),
            Book(
                title="East of Empire",
                author="Erin M.B. O'Halloran",
                year=2021,
                genre="Historical",
                review="""Set against the majestic and turbulent backdrop of a once-mighty empire in its final, chaotic days, "East of Empire" is a sweeping historical saga. The story captures a world on the brink of collapse, where political instability, social upheaval, and the threat of invasion create a volatile atmosphere of fear and opportunity. The narrative moves from opulent palaces to dusty, forgotten provinces, illustrating how the grand tides of change affect people from every walk of life.

The novel weaves together the lives of a diverse cast of characters, each caught in the crosscurrents of history. We meet ambitious courtiers vying for power in the crumbling court, soldiers torn between loyalty to the old ways and the promise of a new order, and ordinary families simply struggling to survive amidst the chaos. Their personal stories of love, loss, betrayal, and ambition are intricately linked, creating a rich tapestry that reflects the larger historical drama unfolding around them.

"East of Empire" is more than a story about the fall of an empire; it is a deeply human exploration of survival and resilience. It examines how people find the strength to endure, adapt, and even thrive when the world they have always known is falling apart. Through its compelling characters and vivid historical setting, the novel paints a powerful portrait of an era defined by both tragic endings and the faint, flickering hope of new beginnings.""",
                imageUri=resourceUri("east_of_empire")),
            Book(
                title="The Secret Keeper of Jaipur",
                author="Alka Joshi",
                year=2021,
                genre="Historical",
                review="""In this highly anticipated sequel to "The Henna Artist," Alka Joshi transports readers back to the vibrant, complex world of 1960s India. The story picks up with Lakshmi, now married and settled in the Himalayan city of Shimla, leaving the hustle and bustle of Jaipur behind. However, her past is not so easily forgotten. Her young protégé, Malik, now a well-educated young man, is beginning his career at the newly constructed Royal Jewel Cinema in Jaipur, a symbol of modernity and progress.

The narrative unfolds through the alternating perspectives of Lakshmi, Malik, and a newcomer, a tribal woman whose path intersects with theirs in a dramatic fashion. When a catastrophic accident occurs at the cinema, the carefully constructed lives of the characters begin to unravel. The incident reveals a web of secrets, hidden motives, and long-buried family dramas, forcing Lakshmi to return to Jaipur and confront the ghosts she thought she had left behind.

"The Secret Keeper of Jaipur" delves into themes of ambition, class, and the intricate, often hidden, lives of women in a rapidly changing India. Malik's investigation into the truth behind the cinema's flawed construction puts him in conflict with powerful people, testing his loyalty and his courage. The novel is a captivating tale of intrigue and a poignant exploration of how secrets, no matter how deeply buried, always find their way to the surface, challenging loyalties and redefining family.""",
                imageUri=resourceUri("the_secret_keeper_of_jaipur")),
            Book(
                title="The Bangalore Detective Club",
                author="Harini Nagendra",
                year=2022,
                genre="Historical",
                review="""Step into the vibrant and colorful world of 1920s Bangalore, a city at the crossroads of colonial British influence and rich Indian tradition. The story introduces Kaveri, a young, intelligent woman who has recently entered an arranged marriage with a busy but kind doctor named Ramu. As a new wife in a new city, she is expected to quietly manage their home and adapt to her new social standing. But Kaveri possesses a sharp mind, a keen eye for detail, and a passion for solving puzzles—qualities that are about to be put to the test.

During a lavish high-society party, a shocking murder occurs, sending ripples of fear through the city's elite. While the police are quick to focus on a likely suspect, Kaveri's observations lead her to believe they are on the wrong track. Unwilling to see an injustice committed, she decides to launch her own discreet investigation, transforming from a new bride into a clever amateur sleuth. With the quiet support of her progressive husband, she begins to navigate the complex social landscape of Bangalore to uncover the truth.

This charming mystery introduces a delightful and witty new detective whose strength lies in her ability to notice the small details that men in power overlook. "The Bangalore Detective Club" is a captivating blend of cozy mystery and rich historical fiction, offering a vivid portrait of a bygone era, from its social customs and delicious cuisine to the underlying tensions of colonial rule. It is the beginning of a wonderful new series that is both a clever whodunit and a celebration of a woman defying the conventions of her time.""",
                imageUri=resourceUri("the_bangalore_detective_club")),
            Book(
                title="The Mistress Of Bhatia House",
                author="Sujata Massey",
                year=2023,
                genre="Mystery",
                review="""Perveen Mistry, Bombay's only female lawyer in the 1920s, returns in a thrilling new mystery that plunges her into the heart of a complex and dangerous family dispute. The case begins when Perveen is appointed by the High Court to execute the will of Mr. Farid, a wealthy and recently deceased textile mill owner. The task seems straightforward until she discovers that all his assets have been signed over to a suspicious charity, leaving his three widows with nothing.

Perveen's investigation takes her to Bhatia House, the secluded family estate on the coast where the widows live in isolation. As she begins to interview the members of the household, she uncovers a tangled web of family secrets, simmering resentments, and financial desperation. The atmosphere is thick with tension and unspoken threats. Her inquiries are met with resistance, and it soon becomes clear that someone is willing to go to great lengths to protect the truth.

The case takes a dark and deadly turn when a murder is committed on the estate, and Perveen finds herself in grave danger. She must use her sharp legal mind and her unique ability to navigate the restricted world of women to solve the case, all while contending with the social and professional prejudices of the time. Sujata Massey masterfully combines a captivating story of justice and intrigue with a rich, immersive portrayal of 1920s Bombay, creating another brilliant installment in a beloved series.""",
                imageUri=resourceUri("the_mistress_of_bhatia_house")),
            Book(
                title="The Half Empress",
                author="Tripti Pandey",
                year=2023,
                genre="Historical",
                review="""This riveting historical novel brings to life the extraordinary story of a woman who rose from obscurity to become one of the most powerful figures of her time. In a world ruled by men, where a woman's influence was expected to be confined to the shadows of the court, she defied every convention. "The Half Empress" charts her incredible journey, from her early life to her strategic marriage into the ruling family, and her eventual grasp of real political power.

The novel vividly portrays the treacherous court politics she had to navigate. She was surrounded by ambitious men who saw her as a mere pawn, but her sharp intelligence, political acumen, and unwavering ambition proved them all wrong. She learned to build alliances, outmaneuver her rivals, and command the loyalty of armies, all while maintaining a careful public persona. She was a master of diplomacy and a ruthless strategist, unafraid to make difficult and sometimes brutal decisions to protect her position and her empire.

More than just a political biography, this is a story about the personal sacrifices and immense pressures she faced. It delves into her complex relationships, her moments of doubt, and the sheer force of will required to rule in a world that was determined to see her fail. "The Half Empress" is a compelling and inspiring account of a formidable woman whose intelligence and ambition not only allowed her to survive but to shape the destiny of an entire empire, leaving a legacy that would be felt for generations.""",
                imageUri=resourceUri("the_half_empress")
            ),
            Book(
                title="The Last King in India",
                author="Rosie Llewellyn-Jones",
                year=2014,
                genre="Historical",
                review="""This poignant and meticulously researched biography tells the story of Wajid Ali Shah, the final king of the wealthy and culturally vibrant kingdom of Awadh in northern India. He was a poet, a playwright, and a patron of the arts, a ruler who presided over a court renowned for its opulence and creativity. However, his reign coincided with the aggressive expansion of the British East India Company, which saw his kingdom as a prize to be taken.

The book chronicles the king's tragic downfall, as the British annexed his kingdom in 1856 under the pretext of misgovernance, forcing him into a life of exile in Calcutta. Stripped of his power and his home, Wajid Ali Shah sought to recreate a miniature version of his beloved capital, Lucknow, in his new surroundings. He continued to support artists and musicians, preserving the rich culture that had defined his rule, even as his political influence faded completely.

Rosie Llewellyn-Jones offers a compelling and nuanced look at this complex historical figure, moving beyond the simplistic colonial narrative that painted him as a debauched and ineffective ruler. Instead, we see a man of culture caught in the inexorable tide of colonial expansion, a symbol of a dying era. "The Last King in India" is a deeply human story about loss, resilience, and the end of an epoch, capturing the sorrowful transition from independent kingdom to colonial subjugation.""",
                imageUri=resourceUri("the_last_king_in_india")
            ),
            Book(
                title="When Women Ruled the World",
                author="Kara Cooney",
                year=2018,
                genre="Novel",
                review="""Egyptologist Kara Cooney offers an engaging and insightful look into a fascinating and often overlooked aspect of ancient history: the reigns of female pharaohs. This book focuses on the lives of six remarkable women who held the throne of Egypt, including Hatshepsut, Nefertiti, and Cleopatra. In a society that was overwhelmingly patriarchal, the ascension of a woman to its highest office was an extraordinary event, often born out of crisis or the failure of the male royal line.

Cooney explores the unique challenges these women faced as they navigated the treacherous world of pharaonic power. To be seen as legitimate rulers, they had to skillfully balance traditional female roles with the masculine symbols of kingship. The book examines the political and personal strategies they employed to gain and maintain their authority, from adopting male titles and regalia to masterfully using propaganda and religious authority to solidify their rule.

"When Women Ruled the World" is not just a collection of biographies; it is an analysis of how power is gendered and how these women's reigns challenge our assumptions about female leadership. Cooney delves into their triumphs in diplomacy, architecture, and warfare, as well as the legacies they left behind—and how those legacies were often deliberately obscured or erased by their male successors. This is a captivating exploration of the lives of powerful women who defied convention to rule one of the greatest civilizations in history.""",
                imageUri=resourceUri("when_women_ruled_the_world")
            ),
            Book(
                title="The Secret Diary of Kasturba",
                author="Neelima Dalmia Adhar",
                year=2016,
                genre="Novel",
                review="""History remembers Mahatma Gandhi as the father of a nation, a global icon of peace and non-violence. But standing quietly beside him throughout his monumental life was his wife, Kasturba. This fictionalized account imagines the inner world of a woman who was a constant but often silent presence in one of history's most dramatic stories. Through the intimate format of a secret diary, the novel gives a voice to the woman who knew Gandhi not as a saint, but as a husband and a man.

The diary offers a unique and deeply personal perspective on their complex and often tumultuous marriage, from their early years as a young couple to their shared struggles in South Africa and India. It explores Kasturba's personal sacrifices, her private frustrations with her husband's vows of celibacy and poverty, and her own quiet acts of rebellion and strength. We see her evolve from a traditional, uneducated child bride into a resilient woman who found her own voice and her own role within the fight for India's independence.

"The Secret Diary of Kasturba" is a powerful work of historical fiction that humanizes an iconic relationship. It shines a light on the personal cost of public greatness and celebrates the unacknowledged strength of a woman who stood in the shadow of a legend. The novel imagines the thoughts and feelings of a wife and mother navigating an extraordinary life, offering a poignant tribute to the unsung partner of a world-changing figure.""",
                imageUri=resourceUri("the_secret_diary_of_kasturba"))
        )
    }
}