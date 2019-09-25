
  ## important: do NOT change the name of this file so that it no longer starts with 'single' !!!
  ## Enter the target state and sample name
  target.state <-20
  name <-"all.test.2."
  
  ### assign names and colours to states, and simplify
  # name the chromatin states you have identified, do NOT use spaces - short names are useful, since they make for better plotting/file names
  # (if not simplifying, then just identify all HMM states in the order you want)
  
  # state names
  simp.names[[name]] <- c(
  	"TrxG_P",
  	"Null _P",
  	"HP1_R", 
  	"PcG_M", 
  	"PcG_R", 
  	"Bivalent", 
  	"TrxG_R", 
  	"Null_R"
  )
  
  # state colours
  # search for 'hex colour codes' if you want to change these or use named colours from http://sape.inf.usi.ch/quick-reference/ggplot2/colour
  simp.cols[[name]] <- c(
  	"#dc3912", # TrxG permissive
  	"#FF9900", # Null p
  	"#4e9a06", # Hp1 repressive
  	"#3366cc", # PcG mixed
  	"#2050aa", # PcG r
  	"#D487D2", # Bivalent
  	"#996666", # TrxG r
  	"#666666"  # Null r
  )
  
  # list of simplified states, with each element being a vector of the states in the simplified state
  # e.g. c(1,5,17) if these are HMM states you want to declare as one chromatin state
  # if not subsuming, identify all states as you did for 'state names'
  simp.states[[name]] <- list(  
  	c(),	# Trxg permissive
  	c(),	# Null permissive
  	c(),    # HP1 repressive
  	c(),    # PcG mixed
  	c(),	# PcG repressive
  	c(),	# Bivalent
  	c(),    # Trxg repressive
  	c() 	# Null repressive
  )
  
